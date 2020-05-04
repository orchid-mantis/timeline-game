(ns timeline-game.db)

(def cards [{:id 0
             :title "Adam"}
            {:id 1
             :title "Noe"}
            {:id 2
             :title "Abraham"}
            {:id 3
             :title "Izák"}
            {:id 4
             :title "Jákob"}
            {:id 5
             :title "David"}
            {:id 6
             :title "Šalomoun"}
            {:id 7
             :title "Eliáš"}
            {:id 8
             :title "Daniel"}])

(def default-db
  {:cards (reduce #(assoc %1 (:id %2) %2) {} cards)})
