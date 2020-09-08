(ns timeline-game.ui.hand.views
  (:require
   [re-frame.core :as rf]
   [timeline-game.ui.hand.events]
   [timeline-game.ui.hand.subs]
   [timeline-game.ui.components :as uic]
   [timeline-game.ui.utils :as utils]))

(defn view []
  (let [player-turn? (rf/subscribe [:players-turn?])
        cards (rf/subscribe [:hand/cards])
        drawn-card-id (rf/subscribe [:drawn-card-id])]
    (fn []
      [:div.hand
       [:ul
        (doall
         (for [card @cards
               :let [id (:id card)]]
           [:li
            {:key id
             :draggable (when @player-turn? true)
             :on-drag-start #(rf/dispatch [:select-card id])
             :on-drag-end (fn []
                            (rf/dispatch [:deselect-card]))
             :style {:display :inline-block}}
            [uic/basic-card-view
             card
             false
             (utils/cs (when @player-turn? :selectable)
                       (when (= id @drawn-card-id) :slide-in-top))
             {:user-select (when (not @player-turn?) :none)
              :cursor (if @player-turn? :pointer :not-allowed)
              :opacity (when (not @player-turn?) 0.3)}]]))]])))