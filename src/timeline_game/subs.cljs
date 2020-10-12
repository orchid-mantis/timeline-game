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
 :game/turn-ready?
 (fn []
   (rf/subscribe [:game/turn]))
 (fn [turn]
   (= turn :ready)))

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
 :game/player-active?
 (fn []
   [(rf/subscribe [:game/players-turn?])
    (rf/subscribe [:game/turn-ready?])])
 (fn [[players-turn? turn-ready?]]
   (and players-turn? turn-ready?)))

(rf/reg-sub
 :game/opponent-active?
 (fn []
   [(rf/subscribe [:game/players-turn?])
    (rf/subscribe [:game/turn-ready?])])
 (fn [[players-turn? turn-ready?]]
   (and (not players-turn?) turn-ready?)))

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
 :game/ended?
 (fn []
   (rf/subscribe [:game/result]))
 (fn [game-result]
   (not= game-result :await)))

(rf/reg-sub
 :allow-action?
 (fn []
   [(rf/subscribe [:game/player-active?])
    (rf/subscribe [:game/ended?])])
 (fn [[player-active? game-ended?]]
   (or player-active? game-ended?)))
