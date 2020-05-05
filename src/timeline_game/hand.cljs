(ns timeline-game.hand
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 :hand
 (fn [db]
   (:hand db)))

(rf/reg-sub
 :hand/cards
 (fn []
   [(rf/subscribe [:cards])
    (rf/subscribe [:hand])])
 (fn [[cards hand]]
   (map cards hand)))

(defn cards-in-hand []
  (let [player-turn? (rf/subscribe [:player-turn?])]
    (fn []
      [:ul
       (doall
        (for [card @(rf/subscribe [:hand/cards])
              :let [id (:id card)]]
          [:li
           {:key id
            :draggable (when @player-turn? true)
            :on-drag-start #(rf/dispatch [:select-card id])
            :on-drag-end (fn []
                           (rf/dispatch [:clear-card-selection]))

            :style {:display :inline-block
                    :margin 5
                    :padding 5
                    :width 100
                    :border "2px solid green"
                    :user-select (when (not @player-turn?) :none)
                    :background-color (when (not @player-turn?) :grey)}}
           (:title card)]))])))
