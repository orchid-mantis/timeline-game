(ns timeline-game.game-loop
  (:require [re-frame.core :as rf]
            [timeline-game.init :as init]
            [timeline-game.fsm :as fsm]))

(def game-state-machine
  {nil              {:new-game       :players-turn}
   :players-turn    {:next-player    :bots-turn}
   :bots-turn       {:next-round     :players-turn
                     :game-end       :game-ended}
   :game-ended      {:new-game       :players-turn}})

(defn update-next-state [db event]
  (fsm/update-next-state game-state-machine db [:game :state] event))

(defn handle-next-state
  [db [event _]]
  (update-next-state db event))

(defn handle-new-game
  [db [event _]]
  (-> db
      init/init-game
      (update-next-state event)))

(defn handle-game-end
  [db [event game-result]]
  (-> db
      (assoc-in [:game :result] game-result)
      (update-next-state event)))

(rf/reg-event-db :new-game handle-new-game)
(rf/reg-event-db :next-player handle-next-state)
(rf/reg-event-db :next-round handle-next-state)
(rf/reg-event-db :game-end handle-game-end)
