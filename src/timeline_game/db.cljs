(ns timeline-game.db)

(def cards [{:title "Adam"}
            {:title "Enoch"}
            {:title "Noe"}
            {:title "Nimrod"}
            {:title "Abraham"}
            {:title "Izák"}
            {:title "Jákob"}
            {:title "Dina"}
            {:title "Josef"}
            {:title "Mojžíš"}
            {:title "Jozue"}
            {:title "Balám"}
            {:title "Raab"}
            {:title "Debora"}
            {:title "Rut"}
            {:title "Gideon"}
            {:title "Jefta"}
            {:title "Samuel"}
            {:title "Saul"}
            {:title "David"}
            {:title "Abigail"}
            {:title "Šalomoun"}
            {:title "Jezábel"}
            {:title "Jošafat"}
            {:title "Eliáš"}
            {:title "Eliša"}
            {:title "Jonáš"}
            {:title "Ezekjáš"}
            {:title "Josijáš"}
            {:title "Jeremjáš"}
            {:title "Daniel"}
            {:title "Ester"}
            {:title "Nehemjáš"}
            {:title "Jan Křtitel"}
            {:title "Marie"}
            {:title "Ježíš"}])

(def default-db
  (let [cards-with-id (map #(assoc %1 :id %2) cards (range))]
    {:cards (reduce #(assoc %1 (:id %2) %2) {} cards-with-id)}))
