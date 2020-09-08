(ns timeline-game.subs
  (:require
   [re-frame.core :as rf]))

(rf/reg-sub
 :deck
 (fn [db]
   (:deck db)))

(rf/reg-sub
 :cards
 (fn [db]
   (:cards db)))

(rf/reg-sub
 :turn
 (fn [db _]
   (get-in db [:game :turn])))

(rf/reg-sub
 :player
 (fn [db _]
   (get-in db [:game :player])))