(ns btg.app.ui.hand.views
  (:require
   [re-frame.core :as rf]
   [reagent.core :as reagent]
   [reagent.dom :as reagent-dom]
   [btg.app.ui.hand.events]
   [btg.app.ui.hand.subs]
   [btg.app.ui.components :as ui]
   [btg.app.ui.utils :as utils]))

(defn draggable [game-card card allow-drag? drawn-card-id]
  (let [s (reagent/atom {:pos [0 0] :z-index 0 :allow-drag? @allow-drag?})
        id (:id card)]
    (reagent/create-class
     {:reagent-render
      (fn [game-card card allow-drag? drawn-card-id]
        (let [z-index (:z-index @s)
              [x y] (:pos @s)
              dragged? (:dragged? @s)]
          [:div.draggable
           {:data-id id
            :style {:touch-action :none
                    :user-select :none
                    :position :relative
                    :z-index z-index
                    :transform (str "translate(" x "px, " y "px)")
                    :transition (when (not dragged?) "0.5s")}}
           [game-card
            card
            false
            {:class (utils/cs (when @allow-drag? :selectable)
                              (when (= id @drawn-card-id) :slide-in-top))
             :style {:cursor (when (not @allow-drag?) :not-allowed)
                     :opacity (when (not @allow-drag?) 0.3)}}]]))

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
                                    (swap! s merge {:dragged? true
                                                    :z-index 1})
                                    (rf/dispatch [:select-card id]))

                         :onend   (fn []
                                    (swap! s merge {:dragged? false
                                                    :z-index 0
                                                    :pos [0 0]})
                                    (rf/dispatch [:deselect-card id]))}])
          (rf/dispatch [:dnd/enable-drag node @allow-drag?])))})))

(defn view []
  (let [game-card (rf/subscribe [:game-card/comp])
        allow-drag? (rf/subscribe [:game/player-active?])
        cards (rf/subscribe [:hand/cards])
        drawn-card-id (rf/subscribe [:drawn-card-id])]
    (fn []
      [:div.hand
       [:div.cards
        (doall
         (for [card @cards]
           ^{:key (:id card)}
           [draggable @game-card card allow-drag? drawn-card-id]))]])))
