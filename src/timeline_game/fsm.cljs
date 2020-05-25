(ns timeline-game.fsm)

(defn next-state
  [fsm current-state transition]
  (get-in fsm [current-state transition]))

(defn update-next-state
  [fsm db path event]
  (if-let [new-state (next-state fsm (get-in db path) event)]
    (assoc-in db path new-state)
    db))
