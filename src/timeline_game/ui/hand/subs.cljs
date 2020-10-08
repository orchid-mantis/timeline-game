(ns timeline-game.ui.hand.subs
  (:require
   [re-frame.core :as rf]))

(rf/reg-sub
 :players-turn?
 (fn []
   [(rf/subscribe [:game/current-player])
    (rf/subscribe [:game/turn])])
 (fn [[player turn]]
   (and (= player :player)
        (= turn :ready))))

(rf/reg-sub
 :hand/state
 (fn [db _]
   (get-in db [:player :hand :state])))

(rf/reg-sub
 :hand/last-added
 (fn [db]
   (get-in db [:player :hand :last-added-id])))

(rf/reg-sub
 :drawn-card-id
 (fn []
   [(rf/subscribe [:hand/state])
    (rf/subscribe [:hand/last-added])])
 (fn [[hand-state last-added-id]]
   (if (= hand-state :draw-card-animation)
     last-added-id
     nil)))

(rf/reg-sub
 :hand/ids
 (fn [db [_ player]]
   (get-in db [player :hand :ids])))

(rf/reg-sub
 :hand/cards
 (fn []
   [(rf/subscribe [:cards])
    (rf/subscribe [:hand/ids :player])])
 (fn [[cards hand]]
   (map cards hand)))