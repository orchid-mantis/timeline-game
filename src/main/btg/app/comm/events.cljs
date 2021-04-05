(ns btg.app.comm.events
  (:require
   [re-frame.core :as rf]
   [btg.app.comm.client :as client]))

(rf/reg-event-db
 :comm/init
 (fn [db _]
   (assoc-in db [:comm] {})))

(rf/reg-event-db
 :comm/connected
 (fn [db [_ value]]
   (assoc-in db [:comm :connected] value)))

(rf/reg-event-db
 :comm/increase
 (fn [db _]
   (update-in db [:comm :push] inc)))

(rf/reg-event-fx
 :comm/connect
 (fn [_ _]
   (client/start!)
   {:dispatch [:comm/connected true]}))