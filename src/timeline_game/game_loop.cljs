(ns timeline-game.game-loop
  (:require [re-frame.core :as rf]
            [timeline-game.fsm :as fsm]))

(def player-fsm
  {nil              {:next-player     :player}
   :player          {:next-player     :bot}
   :bot             {:next-player     :none}})

;; -- Subscriptions -----------------------------------------------------------

(rf/reg-sub
 :player
 (fn [db _]
   (get-in db [:game :player])))

;; -- Events ------------------------------------------------------------------

(defn next-player [db event]
  (fsm/next-state player-fsm (get-in db [:game :player]) event))

(defn update-next-state [db event]
  (fsm/update-next-state player-fsm db [:game :player] event))

(rf/reg-event-fx
 :next-player
 (fn [{:keys [db]} [event _]]
   (let [next-player (next-player db event)]
     {:db (if (= next-player :none)
            (assoc-in db [:game :player] nil)
            (update-next-state db event))
      :dispatch (if (= next-player :none)
                  [:eval-round]
                  [:init-turn])})))
