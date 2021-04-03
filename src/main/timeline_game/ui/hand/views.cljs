(ns timeline-game.ui.hand.views
  (:require
   [re-frame.core :as rf]
   [reagent.core :as reagent]
   [reagent.dom :as reagent-dom]
   [timeline-game.ui.hand.events]
   [timeline-game.ui.hand.subs]
   [timeline-game.ui.utils :as utils]
   [dv.cljs-emotion-reagent :refer [defstyled keyframes]]))

(defn rotation [i o range]
  (* range (/ (- o (/ (- i 1) 2))
              (- i 2))))

(defn offset [i o range]
  (let [num (rotation i o range)]
    (if (pos? num)
      num
      (- num))))

(defn card-transform [max rotation-range offset-range]
  (let [coll (for [card-count (range 1 (inc max))
                   n (range 0 card-count)
                   :let [rotation (rotation card-count n rotation-range)
                         offset (offset card-count n offset-range)]]
               [card-count [(inc n) [rotation offset]]])]
    (reduce (fn [m [k1 [k2 v]]] (assoc-in m [k1 k2] v)) {} coll)))

(def card-transform-precalc (card-transform 10 20 50))

(defn ->keyword [value]
  (keyword (str value)))

(def fade-animation
  (keyframes {"0%"   {:opacity 0.9
                      :transform "scale(1)"}
              "100%" {:opacity 0
                      :transform "scale(1.15)"}}))

(defstyled card :div
  (fn [{:keys [card-transform total-cards n dragged?]}]
    (let [[rotation offset] (get-in card-transform [(->keyword total-cards) (->keyword n)])]
      {:transform (when (not dragged?) (str "translateY(" offset "px) rotate(" rotation "deg)"))
       :backface-visibility :hidden
       :margin "0 -10px"
       ":hover" {:transform "translateY(-50px) rotate(0deg)"
                 :transition-duration "450ms"
                 :z-index 1}
       :transition "800ms cubic-bezier(0.19, 1, 0.22, 1) transform"
       ".parent:after" {:animation :none
                        :background "#fff"
                        :bottom 0
                        :content "''"
                        :left 0
                        :opacity 0
                        :position :absolute
                        :right 0
                        :top 0}
       ":hover .parent:after" {:animation (str fade-animation " 450ms ease-out forwards")
                               :z-index 1}})))

(defn card-in-hand [{:keys [index] :as options} el]
  (card
   (merge options
          {:card-transform card-transform-precalc
           :n (inc index)})
   el))

(defn draggable [game-card card index total-cards allow-drag? drawn-card-id]
  (let [s (reagent/atom {:pos [0 0] :z-index 0 :allow-drag? @allow-drag?})
        id (:id card)]
    (reagent/create-class
     {:reagent-render
      (fn [game-card card index total-cards allow-drag? drawn-card-id]
        (let [[x y] (:pos @s)
              dragged? (:dragged? @s)]
          [:div.draggable
           {:data-id id
            :class (when dragged? :dragged)
            :style {:touch-action :none
                    :user-select :none
                    :position :relative
                    :transform (str "translate(" x "px, " y "px)")
                    :transition (when (not dragged?) "0.5s")}}
           (card-in-hand
            {:total-cards total-cards
             :index index
             :dragged? dragged?}
            [game-card
             card
             false
             {:class (utils/cs (when @allow-drag? :selectable)
                               (when (= id @drawn-card-id) :slide-in-top))
              :style {:cursor (when (not @allow-drag?) :not-allowed)
                      :opacity (when (not @allow-drag?) 0.3)}}])]))

      :component-did-update
      (fn [this]
        (let [node (reagent-dom/dom-node this)]
          (when (not= @allow-drag? (:allow-drag? @s))
            (swap! s update :allow-drag? not)
            (rf/dispatch [:dnd/enable-drag node @allow-drag?]))))

      :component-did-mount
      (fn [this]
        (let [node (reagent-dom/dom-node this)]
          (rf/dispatch [:dnd/draggable
                        node
                        {:inertia true
                         :modifiers {:restriction ".table", :endOnly false}
                         :onmove (fn [e]
                                   (let [[x y] (:pos @s)
                                         new-x (+ (.-dx e) x)
                                         new-y (+ (.-dy e) y)]
                                     (swap! s assoc :pos [new-x new-y])))

                         :onstart (fn []
                                    (swap! s merge {:dragged? true})
                                    (rf/dispatch [:select-card id]))

                         :onend   (fn []
                                    (swap! s merge {:dragged? false
                                                    :pos [0 0]})
                                    (rf/dispatch [:deselect-card id]))}])
          (rf/dispatch [:dnd/enable-drag node @allow-drag?])))})))

(defn view []
  (let [game-card (rf/subscribe [:game-card/comp])
        allow-drag? (rf/subscribe [:game/player-active?])
        cards (rf/subscribe [:hand/cards])
        drawn-card-id (rf/subscribe [:drawn-card-id])]
    (fn []
      (let [total-cards (count @cards)]
        [:div.hand
         (doall
          (map-indexed
           (fn [index card]
             ^{:key (:id card)}
             [draggable @game-card card index total-cards allow-drag? drawn-card-id])
           @cards))]))))
