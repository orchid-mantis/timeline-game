(ns timeline-game.fsm)

(def turn-state-fsm
  {nil                  {:init-turn           :ready}
   :ready               {:correct-move        :well-placed-card
                         :wrong-move          :misplaced-card}
   :well-placed-card    {:correct-end-turn    :turn-ended}
   :misplaced-card      {:wrong-end-turn      :turn-ended}
   :turn-ended          {:init-turn           :ready}})

(def player-fsm
  {nil        {:next-player    :player}
   :player    {:next-player    :bot}
   :bot       {:next-player    :none}})

(def hand-fsm
  {:ready                  {:draw-card        :draw-card-animation}
   :draw-card-animation    {:end-draw-card    :ready}})

(defn next-state
  [fsm current-state transition]
  (get-in fsm [current-state transition]))

(defn update-next-state
  [fsm db path event]
  (if-let [new-state (next-state fsm (get-in db path) event)]
    (assoc-in db path new-state)
    db))

(defn update-turn [db event]
  (update-next-state turn-state-fsm db [:game :turn] event))

(defn update-player [db event]
  (update-next-state player-fsm db [:game :player-fsm] event))

(defn next-player [db event]
  (next-state player-fsm (get-in db [:game :player-fsm]) event))

(defn update-hand [db event player]
  (update-next-state hand-fsm db [player :hand :state] event))
