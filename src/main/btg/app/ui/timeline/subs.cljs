(ns btg.app.ui.timeline.subs
  (:require
   [re-frame.core :as rf]))

(rf/reg-sub
 :timeline-ids
 (fn [db]
   (get-in db [:timeline :ids])))

(rf/reg-sub
 :timeline/last-added
 (fn [db]
   (get-in db [:timeline :last-added-id])))

(rf/reg-sub
 :timeline/cards
 (fn []
   [(rf/subscribe [:cards])
    (rf/subscribe [:timeline-ids])])
 (fn [[cards timeline]]
   (vec (map cards timeline))))

(rf/reg-sub
 :timeline/scrollable?
 (fn [db]
   (get-in db [:timeline :scrollable?])))

(rf/reg-sub
 :move-animation
 (fn []
   (rf/subscribe [:game/turn]))
 (fn [turn]
   (case turn
     :well-placed-card :flip-in-hor-top
     :misplaced-card :rotate-out-2-cw
     nil)))

(rf/reg-sub
 :player/selected-cards
 (fn [db]
   (get-in db [:player :selected-cards])))

(rf/reg-sub
 :highlight-drop-zones?
 (fn []
   (rf/subscribe [:player/selected-cards]))
 (fn [selected-cards]
   (= 1 (count selected-cards))))

(rf/reg-sub
 :allow-drop?
 (fn []
   (rf/subscribe [:player/selected-cards]))
 (fn [selected-cards]
   (<= (count selected-cards) 1)))