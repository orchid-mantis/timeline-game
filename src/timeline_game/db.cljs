(ns timeline-game.db)

(def cards [{:title "Adam"}
            {:title "Noe"}
            {:title "Abraham"}
            {:title "Izák"}
            {:title "Jákob"}
            {:title "David"}
            {:title "Šalomoun"}
            {:title "Eliáš"}
            {:title "Daniel"}])

(def default-db
  (let [cards-with-id (map #(assoc %1 :id %2) cards (range))]
    {:cards (reduce #(assoc %1 (:id %2) %2) {} cards-with-id)}))
