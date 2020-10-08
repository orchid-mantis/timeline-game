(ns timeline-game.ui.hand.views
  (:require
   [re-frame.core :as rf]
   [reagent.core :as reagent]
   [reagent.dom :as reagent-dom]
   [timeline-game.ui.hand.events]
   [timeline-game.ui.hand.subs]
   [timeline-game.ui.components :as uic]
   [timeline-game.ui.utils :as utils]))

(defn draggable [card action-allowed? drawn-card-id]
  (let [s (reagent/atom {:pos [0 0] :z-index 0 :action-allowed? @action-allowed?})
        id (:id card)]
    (reagent/create-class
     {:reagent-render
      (fn [card action-allowed? drawn-card-id]
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
           [uic/basic-card-view
            card
            false
            (utils/cs (when @action-allowed? :selectable)
                      (when (= id @drawn-card-id) :slide-in-top))
            {:cursor (when (not @action-allowed?) :not-allowed)
             :opacity (when (not @action-allowed?) 0.3)}]]))

      :component-did-update
      (fn [this]
        (let [node (reagent-dom/dom-node this)]
          (when (not= @action-allowed? (:action-allowed? @s))
            (swap! s update :action-allowed? not)
            (rf/dispatch [:dnd/enable-drag node @action-allowed?]))))

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
          (rf/dispatch [:dnd/enable-drag node @action-allowed?])))})))

(defn view []
  (let [action-allowed? (rf/subscribe [:user-action/allowed?])
        cards (rf/subscribe [:hand/cards])
        drawn-card-id (rf/subscribe [:drawn-card-id])]
    (fn []
      [:div.hand
       (doall
        (for [card @cards]
          [:div {:key (:id card)}
           [draggable card action-allowed? drawn-card-id]]))])))
