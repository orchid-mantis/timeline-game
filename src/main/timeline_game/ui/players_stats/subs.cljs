(ns timeline-game.ui.players-stats.subs
  (:require
   [re-frame.core :as rf]))

(rf/reg-sub
 :hand/size
 (fn [[_ player]]
   (rf/subscribe [:hand/ids player]))
 (fn [hand-ids]
   (count hand-ids)))

(rf/reg-sub
 :player/stats
 (fn [db [_ player]]
   (get-in db [player :stats])))

(rf/reg-sub
 :player/stats-view
 (fn [[_ player]]
   [(rf/subscribe [:cards])
    (rf/subscribe [:player/stats player])])
 (fn [[cards stats]]
   (let [id (:last-played-card-id stats)
         card (cards id)]
     (merge stats {:last-played-card card}))))