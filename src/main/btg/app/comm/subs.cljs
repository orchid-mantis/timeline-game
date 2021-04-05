(ns btg.app.comm.subs
  (:require
   [re-frame.core :as rf]))

(rf/reg-sub
 :comm/push-count
 (fn [db _]
   (get-in db [:comm :push])))

(rf/reg-sub
 :comm/connected
 (fn [db _]
   (true? (get-in db [:comm :connected]))))
