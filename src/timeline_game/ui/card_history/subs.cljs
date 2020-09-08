(ns timeline-game.ui.card-history.subs
  (:require
   [re-frame.core :as rf]
   [timeline-game.common :refer [fmap copy-fields]]))

(rf/reg-sub
 :players
 (fn [db]
   (get-in db [:players])))

(rf/reg-sub
 :history
 (fn [db]
   (get-in db [:history])))

(rf/reg-sub
 :history/played-cards
 (fn []
   [(rf/subscribe [:cards])
    (rf/subscribe [:history])])
 (fn [[cards history]]
   (let [played-cards (fmap (partial copy-fields cards :id [:title]) history)]
     (for [[k v] played-cards]
       (assoc v :round k)))))