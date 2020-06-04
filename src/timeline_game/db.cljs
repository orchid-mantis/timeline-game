(ns timeline-game.db)

(def cards [{:title "Stvoření"
             :year -9999
             :time-desc "„Na počátku“"}
            {:title "Adam a Eva"
             :year -4026
             :time-desc "4026 př.n.l"}
            {:title "Enoch"
             :year -3404
             :time-desc "3404-3039 př.n.l"}
            {:title "Nefilim"
             :year -3040
             :time-desc "3040-2370 př.n.l"}
            {:title "Stavba archy"
             :year -2420
             :time-desc "2420-2370 př.n.l"}
            {:title "Babylónská věž"
             :year -2268
             :time-desc "> 2269 př.n.l"}
            {:title "Abraham"
             :year -2018
             :time-desc "2018-1843 př.n.l"}
            {:title "Zkouška Abrahama"
             :year -1893
             :time-desc "1893 př.n.l"}
            {:title "Jákob"
             :year -1858
             :time-desc "*1858 př.n.l"}
            {:title "Dina"
             :year -1750
             :time-desc "1750 př.n.l"}
            {:title "Josef prodán"
             :year -1750
             :time-desc "1750 př.n.l"}
            {:title "Narození Mojžíše"
             :year -1593
             :time-desc "1593 př.n.l"}
            {:title "Jozue"
             :year -1473
             :time-desc "1473 př.n.l"}
            {:title "Balámova oslice"
             :year -1480
             :time-desc "1480 př.n.l"}
            {:title "Raab"
             :year -1473
             :time-desc "1473 př.n.l"}
            {:title "Debora"
             :year -1450
             :time-desc "1450 př.n.l"}
            {:title "Noemi a Rut"
             :year -1210
             :time-desc "1210 př.n.l"}
            {:title "Gideon"
             :year -1210
             :time-desc "1210 př.n.l"}
            {:title "Jeftova dcera"
             :year -1205
             :time-desc "1205 př.n.l"}
            {:title "Samuel"
             :year -1200
             :time-desc "1200 př.n.l"}
            {:title "Saul - první král"
             :year -1117
             :time-desc "1117 př.n.l"}
            {:title "David"
             :year -1100
             :time-desc "1100 př.n.l"}
            {:title "Abigail"
             :year -1070
             :time-desc "1070 př.n.l"}
            {:title "Šalomoun"
             :year -1035
             :time-desc "1035 př.n.l"}
            {:title "Jezábel"
             :year -940
             :time-desc "940 př.n.l"}
            {:title "Elijáš"
             :year -940
             :time-desc "940 př.n.l"}
            {:title "Jehošafat"
             :year -930
             :time-desc "930 př.n.l"}
            {:title "Izraleská dívenka"
             :year -900
             :time-desc "900 př.n.l"}
            {:title "Jonáš"
             :year -840
             :time-desc "840 př.n.l"}
            {:title "Ezekjáš"
             :year -740
             :time-desc "740 př.n.l"}
            {:title "Josijáš"
             :year -659
             :time-desc "659-628 př.n.l"}
            {:title "Jeremjáš"
             :year -640
             :time-desc "640 př.n.l"}
            {:title "Daniel"
             :year -538
             :time-desc "538 př.n.l"}
            {:title "Ester"
             :year -480
             :time-desc "480 př.n.l"}
            {:title "Hradby Jeruzaléma"
             :year -455
             :time-desc "455 př.n.l"}
            {:title "Marie"
             :year -2
             :time-desc "2 př.n.l"}
            {:title "Astrologové"
             :year 1
             :time-desc "1 n.l"}
            {:title "Křest Ježíše"
             :year 29
             :time-desc "29 n.l"}
            {:title "„Nová země“"
             :year 9999
             :time-desc "Již brzy..."}])

(def default-db
  (let [cards-with-id (map #(assoc %1 :id %2) cards (range))]
    {:cards (reduce #(assoc %1 (:id %2) %2) {} cards-with-id)}))
