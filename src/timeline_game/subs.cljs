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
   (get-in db [:game :curr-player])))

(rf/reg-sub
 :game/players-turn?
 (fn []
   (rf/subscribe [:game/current-player]))
 (fn [curr-player]
   (= curr-player :player)))

(rf/reg-sub
 :game/round
 (fn [db _]
   (get-in db [:game :round])))

(rf/reg-sub
 :game/mode
 (fn [db _]
   (get-in db [:game :mode])))

(rf/reg-sub
 :game/result
 (fn [db]
   (get-in db [:game :result])))

(rf/reg-sub
 :allow-new-game?
 (fn []
   [(rf/subscribe [:game/result])
    (rf/subscribe [:game/turn])
    (rf/subscribe [:game/current-player])])
 (fn [[game-result turn curr-player]]
   (let [game-ended? (not= game-result :await)
         players-turn? (and (= curr-player :player) (= turn :ready))]
     (or players-turn?
         game-ended?))))
