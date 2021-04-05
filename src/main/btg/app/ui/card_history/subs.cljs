(ns btg.app.ui.card-history.subs
  (:require
   [re-frame.core :as rf]
   [btg.app.common :refer [map-v copy-fields]]))

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
   (let [played-cards (map-v (partial copy-fields cards :id [:title]) history)]
     (for [[k v] played-cards]
       (assoc v :round k)))))