(ns timeline-game.ui.hand.views
  (:require
   [re-frame.core :as rf]
   [reagent.core :as reagent]
   [reagent.dom :as reagent-dom]
   ["interactjs" :as interact]
   [timeline-game.ui.hand.events]
   [timeline-game.ui.hand.subs]
   [timeline-game.ui.components :as uic]
   [timeline-game.ui.utils :as utils]))

(defn draggable [card player-turn? drawn-card-id]
  (let [s (atom {:pos [0 0]})
        id (:id card)]
    (reagent/create-class
     {:reagent-render
      (fn [card player-turn? drawn-card-id]
        (let [node (:node @s)]
          (when node
            (.draggable (interact node) @player-turn?))
          [:div.draggable
           {:style {:touch-action :none
                    :user-select :none}}
           [uic/basic-card-view
            card
            false
            (utils/cs (when @player-turn? :selectable)
                      (when (= id @drawn-card-id) :slide-in-top))
            {:cursor (when (not @player-turn?) :not-allowed)
             :opacity (when (not @player-turn?) 0.3)}]]))

      :component-did-mount
      (fn [this]
        (let [node (reagent-dom/dom-node this)]
          (swap! s assoc :node node)
          (.draggable (interact node)
                      #js {:inertia true
                           :modifiers
                           #js
                            [(.restrictRect
                              (.-modifiers interact)
                              #js {:restriction ".table", :endOnly false})]
                           :onmove (fn [e]
                                     (let [[x y] (:pos @s)
                                           new-x (+ (.-dx e) x)
                                           new-y (+ (.-dy e) y)
                                           target (.-target e)]
                                       (swap! s assoc :pos [new-x new-y])
                                       (set!
                                        (.. target -style -webkitTransform)
                                        (set!
                                         (.. target -style -transform)
                                         (str "translate(" new-x "px, " new-y "px)")))))

                           :onstart #(rf/dispatch [:select-card id])

                           :onend   #(rf/dispatch [:deselect-card])})))})))

(defn view []
  (let [player-turn? (rf/subscribe [:players-turn?])
        cards (rf/subscribe [:hand/cards])
        drawn-card-id (rf/subscribe [:drawn-card-id])]
    (fn []
      [:div.hand
       [:ul
        (doall
         (for [card @cards]
           [:div {:key (:id card)
                  :style {:display :inline-block}}
            [draggable  card player-turn? drawn-card-id]]))]])))
