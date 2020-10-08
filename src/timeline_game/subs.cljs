(ns timeline-game.subs
  (:require
   [re-frame.core :as rf]))

(rf/reg-sub
 :players
 (fn [db _]
   (get-in db [:players])))

(rf/reg-sub
 :deck
 (fn [db]
   (:deck db)))

(rf/reg-sub
 :cards
 (fn [db]
   (:cards db)))

(rf/reg-sub
 :game/turn
 (fn [db _]
   (get-in db [:game :turn])))

(rf/reg-sub
 :game/current-player
 (fn [db _]
   (get-in db [:game :player])))

(rf/reg-sub
 :game/round
 (fn [db _]
   (get-in db [:game :round])))

(rf/reg-sub
 :game/mode
 (fn [db _]
   (get-in db [:game :mode])))
