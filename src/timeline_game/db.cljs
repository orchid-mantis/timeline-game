(ns timeline-game.db)

(def cards [{:title "Stvoření", :year -9999, :time-desc "„Na počátku“"}
            {:title "Zahrada Eden", :year -9998, :time-desc "„Na počátku“"}
            {:title "Adam a Eva", :year -4026, :time-desc "4026 př.n.l"}
            {:title "Vyhnání z ráje", :year -4025, :time-desc "> 4026 př.n.l"}
            {:title "První děti", :year -4000, :time-desc "< 3096 př.n.l"}
            {:title "Kain a Abel", :year -3900, :time-desc "3900 př.n.l"}
            {:title "Enoch", :year -3404, :time-desc "3404-3039 př.n.l"}
            {:title "Nefilim", :year -3040, :time-desc "3040-2370 př.n.l"}
            {:title "Stavba archy", :year -2420, :time-desc "2420-2370 př.n.l"}
            {:title "Potopa", :year -2370, :time-desc "2370 př.n.l"}
            {:title "První duha", :year -2369, :time-desc "2369 př.n.l"}
            {:title "Babylónská věž", :year -2268, :time-desc "> 2269 př.n.l"}
            {:title "Abraham", :year -2018, :time-desc "2018-1843 př.n.l"}
            {:title "Lotova manželka", :year -1933, :time-desc "1933 př.n.l"}
            {:title "Zkouška Abrahama", :year -1893, :time-desc "1893 př.n.l"}
            {:title "Rebeka", :year -1878, :time-desc "1878 př.n.l"}
            {:title "Jákob", :year -1858, :time-desc "*1858 př.n.l"}
            {:title "Jákobovi synové", :year -1780, :time-desc "1770 př.n.l"}
            {:title "Ráchel", :year -1780, :time-desc "1780 př.n.l"}
            {:title "Josef prodán", :year -1750, :time-desc "1750 př.n.l"}
            {:title "Dina", :year -1750, :time-desc "1750 př.n.l"}
            {:title "Faraónovy sny", :year -1740, :time-desc "1740 př.n.l"}
            {:title "Josef ve vězení", :year -1740, :time-desc "1740 př.n.l"}
            {:title "Zkouška bratrů", :year -1736, :time-desc "> 1737 př.n.l"}
            {:title "Josef vítá Jákoba", :year -1728, :time-desc "1728 př.n.l"}
            {:title "Job", :year -1612, :time-desc "< 1613 př.n.l"}
            {:title "Otroctví v Egyptě", :year -1600, :time-desc "1600 př.n.l"}
            {:title "Narození Mojžíše", :year -1593, :time-desc "1593 př.n.l"}
            {:title "Mojžíš prchá", :year -1553, :time-desc "1553 př.n.l"}
            {:title "U Faraóna", :year -1514, :time-desc "1514-1513 př.n.l"}
            {:title "Hořící keř", :year -1514, :time-desc "1514 př.n.l"}
            {:title "Zlaté tele", :year -1513, :time-desc "1513 př.n.l"}
            {:title "Desatero", :year -1513, :time-desc "1513 př.n.l"}
            {:title "Mana", :year -1513, :time-desc "1513-1473 př.n.l"}
            {:title "Rudé moře", :year -1513, :time-desc "1513 př.n.l"}
            {:title "Deset ran", :year -1513, :time-desc "1513 př.n.l"}
            {:title "Svatostánek", :year -1512, :time-desc "1512 př.n.l"}
            {:title "Izraelští zvědové", :year -1510, :time-desc "1510 př.n.l"}
            {:title "Áron", :year -1505, :time-desc "1505 př.n.l"}
            {:title "Měděný had", :year -1500, :time-desc "1500 př.n.l"}
            {:title "Voda ze skály", :year -1500, :time-desc "1500 př.n.l"}
            {:title "Balámova oslice", :year -1480, :time-desc "1480 př.n.l"}
            {:title "Achan", :year -1473, :time-desc "1473 př.n.l"}
            {:title "Jericho", :year -1473, :time-desc "1473 př.n.l"}
            {:title "Přejití Jordánu", :year -1473, :time-desc "1473 př.n.l"}
            {:title "Raab", :year -1473, :time-desc "1473 př.n.l"}
            {:title "Jozue", :year -1473, :time-desc "1473 př.n.l"}
            {:title "„Slunce, zastav se!“" :year -1460 :time-desc "1460 př.n.l"}
            {:title "Gibeoňané", :year -1460, :time-desc "1460 př.n.l"}
            {:title "Debora", :year -1450, :time-desc "1450 př.n.l"}
            {:title "Gideon", :year -1210, :time-desc "1210 př.n.l"}
            {:title "Noemi a Rut", :year -1210, :time-desc "1210 př.n.l"}
            {:title "Samson", :year -1205, :time-desc "1205 př.n.l"}
            {:title "Jeftova dcera", :year -1205, :time-desc "1205 př.n.l"}
            {:title "Samuel", :year -1200, :time-desc "1200 př.n.l"}
            {:title "První král", :year -1117, :time-desc "1117 př.n.l"}
            {:title "Goliáš", :year -1100, :time-desc "1100 př.n.l"}
            {:title "David", :year -1100, :time-desc "1100 př.n.l"}
            {:title "Saulova nenávist", :year -1080, :time-desc "1080 př.n.l"}
            {:title "David králem", :year -1070, :time-desc "1070 př.n.l"}
            {:title "Abigail", :year -1070, :time-desc "1070 př.n.l"}
            {:title "Davidův hřích", :year -1050, :time-desc "1050 př.n.l"}
            {:title "Šalomoun", :year -1035, :time-desc "1035 př.n.l"}
            {:title "Postaven chrám", :year -1027, :time-desc "1027 př.n.l"}
            {:title "Dělení království", :year -997, :time-desc "997 př.n.l"}
            {:title "Elijáš", :year -940, :time-desc "940 př.n.l"}
            {:title "Jezábel", :year -940, :time-desc "940 př.n.l"}
            {:title "Jehošafat", :year -930, :time-desc "930 př.n.l"}
            {:title "Izraleská dívenka", :year -900, :time-desc "900 př.n.l"}
            {:title "Jonáš", :year -840, :time-desc "840 př.n.l"}
            {:title "Ezekjáš", :year -740, :time-desc "740 př.n.l"}
            {:title "Izajáš prorokuje", :year -700, :time-desc "700 př.n.l"}
            {:title "Josijáš", :year -659, :time-desc "659-628 př.n.l"}
            {:title "Jeremjáš", :year -640, :time-desc "640 př.n.l"}
            {:title "V Babylóně", :year -617, :time-desc "617 př.n.l"}
            {:title "Zničení Jeruzaléma", :year -607, :time-desc "607 př.n.l"}
            {:title "Zlatá socha", :year -600, :time-desc "600 př.n.l"}
            {:title "„MENE, MENE, ...“", :year -539, :time-desc "539 př.n.l"}
            {:title "Daniel", :year -538, :time-desc "538 př.n.l"}
            {:title "Návrat z Babylóna", :year -537, :time-desc "537 př.n.l"}
            {:title "Ester", :year -480, :time-desc "480 př.n.l"}
            {:title "Ezra", :year -460, :time-desc "460 př.n.l"}
            {:title "Hradby Jeruzaléma", :year -455, :time-desc "455 př.n.l"}
            {:title "Narození Ježíše", :year -2, :time-desc "2 př.n.l"}
            {:title "Marie", :year -2, :time-desc "2 př.n.l"}
            {:title "Astrologové", :year 1, :time-desc "1 n.l."}
            {:title "Ježíšovi 12 let", :year 10, :time-desc "10 n.l."}
            {:title "Křest Ježíše", :year 29, :time-desc "29 n.l."}
            {:title "Samaritánka", :year 30, :time-desc "30 n.l."}
            {:title "Očista chrámu", :year 30, :time-desc "30 n.l."}
            {:title "Ježíš a děti", :year 31, :time-desc "31-33 n.l."}
            {:title "Samaritán", :year 31, :time-desc "31-33 n.l."}
            {:title "Nasycení tisíců lidí", :year 31, :time-desc "31-33 n.l."}
            {:title "Jairova dcera", :year 31, :time-desc "31-33 n.l."}
            {:title "Ježíš léčí", :year 31, :time-desc "31-33 n.l."}
            {:title "Kázání na hoře", :year 31, :time-desc "31 n.l."}
            {:title "Návrat do nebe", :year 33, :time-desc "33 n.l."}
            {:title "Svatý duch", :year 33, :time-desc "33 n.l."}
            {:title "Tomáš", :year 33, :time-desc "33 n.l."}
            {:title "Ježíš vzkříšen", :year 33, :time-desc "33 n.l."}
            {:title "Výkupní oběť", :year 33, :time-desc "33 n.l."}
            {:title "Jidáš", :year 33, :time-desc "33 n.l."}
            {:title "Pánova večeře", :year 33, :time-desc "33 n.l."}
            {:title "„Kdy to bude?“", :year 33, :time-desc "33 n.l."}
            {:title "Král na oslátku", :year 33, :time-desc "33 n.l."}
            {:title "Propuštění z vězení", :year 33, :time-desc "33 n.l."}
            {:title "Saul z Tarsu", :year 34, :time-desc "34 n.l."}
            {:title "Štěpán", :year 34, :time-desc "34 n.l."}
            {:title "Kornelius", :year 36, :time-desc "36 n.l."}
            {:title "Pavel a Timoteus", :year 47, :time-desc "47-52 n.l."}
            {:title "Eutychos", :year 52, :time-desc "52-56 n.l."}
            {:title "Ztroskotání", :year 58, :time-desc "58-59 n.l."}
            {:title "Pavel v Římě", :year 59, :time-desc "59-61 n.l."}
            {:title "Zjevení Janovi", :year 96, :time-desc "96 n.l."}
            {:title "„Nová země“", :year 9999, :time-desc "Již brzy..."}])

(defn indexed-cards []
  (let [cards-with-id (map #(assoc %1 :id %2) cards (range))]
    {:cards (reduce #(assoc %1 (:id %2) %2) {} cards-with-id)}))

(def default-db
  (indexed-cards))
