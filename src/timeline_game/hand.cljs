(ns timeline-game.hand
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 :players-turn?
 (fn [] (rf/subscribe [:game-state]))
 (fn [game-state]
   (= game-state :players-turn)))

(rf/reg-sub
 :hand
 (fn [db]
   (get-in db [:player :hand])))

(rf/reg-sub
 :hand/cards
 (fn []
   [(rf/subscribe [:cards])
    (rf/subscribe [:hand])])
 (fn [[cards hand]]
   (map cards hand)))

(defn view []
  (let [player-turn? (rf/subscribe [:players-turn?])
        cards (rf/subscribe [:hand/cards])]
    (fn []
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

            :style {:display :inline-block
                    :margin 5
                    :padding 5
                    :width 100
                    :border "2px solid blue"
                    :user-select (when (not @player-turn?) :none)
                    :cursor :pointer
                    :background-color (when (not @player-turn?) :grey)}}
           (:title card)]))])))
