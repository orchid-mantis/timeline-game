(ns timeline-game.cards
  (:require
   [re-frame.core :as rf]))

(def cards [{:title "Stvoření", :year -9999, :time-desc "„Na počátku“" :img-name "1" :scroll-text {:font-size "0.8em" :indent "5.5%" :top "-0.35em"}}
            {:title "Zahrada Eden", :year -9998, :time-desc "„Na počátku“" :img-name "3" :scroll-text {:font-size "0.8em" :indent "5.5%" :top "-0.35em"}}
            {:title "Adam a Eva", :year -4026, :time-desc "4026 př.n.l." :img-name "4" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Vyhnání z ráje", :year -4025, :time-desc "> 4026 př.n.l." :img-name "5" :scroll-text {:font-size "0.8em" :indent "6%" :top "-0.35em"}}
            {:title "První děti", :year -4000, :time-desc "< 3096 př.n.l." :img-name "6" :scroll-text {:font-size "0.8em" :indent "6%" :top "-0.35em"}}
            {:title "Kain a Abel", :year -3900, :time-desc "3900 př.n.l." :img-name "7" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Enoch", :year -3404, :time-desc "3404-3039 př.n.l." :img-name "13" :scroll-text {:font-size "0.65em" :indent "3.5%" :top "-0.55em"}}
            {:title "Nefilim", :year -3040, :time-desc "3040-2370 př.n.l." :img-name "12" :scroll-text {:font-size "0.65em" :indent "3.5%" :top "-0.55em"}}
            {:title "Stavba archy", :year -2420, :time-desc "2420-2370 př.n.l." :img-name "11" :scroll-text {:font-size "0.65em" :indent "3.5%" :top "-0.55em"}}
            {:title "Potopa", :year -2370, :time-desc "2370 př.n.l." :img-name "10" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "První duha", :year -2369, :time-desc "2369 př.n.l." :img-name "9" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Babylónská věž", :year -2268, :time-desc "> 2269 př.n.l." :img-name "8" :scroll-text {:font-size "0.8em" :indent "6%" :top "-0.35em"}}
            {:title "Abraham", :year -2018, :time-desc "2018-1843 př.n.l." :img-name "14" :scroll-text {:font-size "0.65em" :indent "3.5%" :top "-0.55em"}}
            {:title "Lotova manželka", :year -1933, :time-desc "1933 př.n.l." :img-name "16" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Zkouška Abrahama", :year -1893, :time-desc "1893 př.n.l." :img-name "15" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Rebeka", :year -1878, :time-desc "1878 př.n.l." :img-name "17" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Jákob", :year -1858, :time-desc "*1858 př.n.l." :img-name "18" :scroll-text {:font-size "0.8em" :indent "7%" :top "-0.35em"}}
            {:title "Jákobovi synové", :year -1780, :time-desc "1770 př.n.l." :img-name "20" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Ráchel", :year -1780, :time-desc "1780 př.n.l." :img-name "19" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Josef prodán", :year -1750, :time-desc "1750 př.n.l." :img-name "22" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Dina", :year -1750, :time-desc "1750 př.n.l." :img-name "21" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Faraónovy sny", :year -1740, :time-desc "1740 př.n.l." :img-name "24" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Josef ve vězení", :year -1740, :time-desc "1740 př.n.l." :img-name "23" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Zkouška bratrů", :year -1736, :time-desc "> 1737 př.n.l." :img-name "25" :scroll-text {:font-size "0.8em" :indent "6%" :top "-0.35em"}}
            {:title "Josef vítá Jákoba", :year -1728, :time-desc "1728 př.n.l." :img-name "26" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Job", :year -1612, :time-desc "< 1613 př.n.l." :img-name "27" :scroll-text {:font-size "0.8em" :indent "6%" :top "-0.35em"}}
            {:title "Otroctví v Egyptě", :year -1600, :time-desc "1600 př.n.l." :img-name "28" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Narození Mojžíše", :year -1593, :time-desc "1593 př.n.l." :img-name "29" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Mojžíš prchá", :year -1553, :time-desc "1553 př.n.l." :img-name "36" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}} 
            {:title "U Faraóna", :year -1514, :time-desc "1514-1513 př.n.l." :img-name "31" :scroll-text {:font-size "0.65em" :indent "3.5%" :top "-0.55em"}}
            {:title "Hořící keř", :year -1514, :time-desc "1514 př.n.l." :img-name "30" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}} 
            {:title "Zlaté tele", :year -1513, :time-desc "1513 př.n.l." :img-name "37" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Desatero", :year -1513, :time-desc "1513 př.n.l." :img-name "35" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Mana", :year -1513, :time-desc "1513-1473 př.n.l." :img-name "34" :scroll-text {:font-size "0.65em" :indent "3.5%" :top "-0.55em"}}
            {:title "Rudé moře", :year -1513, :time-desc "1513 př.n.l." :img-name "33" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Deset ran", :year -1513, :time-desc "1513 př.n.l." :img-name "32" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Svatostánek", :year -1512, :time-desc "1512 př.n.l." :img-name "38" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Izraelští zvědové", :year -1510, :time-desc "1510 př.n.l." :img-name "39" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Áron", :year -1505, :time-desc "1505 př.n.l." :img-name "40" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Měděný had", :year -1500, :time-desc "1500 př.n.l." :img-name "42" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Voda ze skály", :year -1500, :time-desc "1500 př.n.l." :img-name "41" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Balámova oslice", :year -1480, :time-desc "1480 př.n.l." :img-name "43" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Achan", :year -1473, :time-desc "1473 př.n.l." :img-name "48" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Jericho", :year -1473, :time-desc "1473 př.n.l." :img-name "47" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Přejití Jordánu", :year -1473, :time-desc "1473 př.n.l." :img-name "46" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Raab", :year -1473, :time-desc "1473 př.n.l." :img-name "45" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Jozue", :year -1473, :time-desc "1473 př.n.l." :img-name "44" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "„Slunce, zastav se!“" :year -1460 :time-desc "1460 př.n.l." :img-name "50" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Gibeoňané", :year -1460, :time-desc "1460 př.n.l." :img-name "49" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Debora", :year -1450, :time-desc "1450 př.n.l." :img-name "51" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Gideon", :year -1210, :time-desc "1210 př.n.l." :img-name "53" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Noemi a Rut", :year -1210, :time-desc "1210 př.n.l." :img-name "52" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Samson", :year -1205, :time-desc "1205 př.n.l." :img-name "55" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Jeftova dcera", :year -1205, :time-desc "1205 př.n.l." :img-name "54" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Samuel", :year -1200, :time-desc "1200 př.n.l." :img-name "56" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "První král", :year -1117, :time-desc "1117 př.n.l." :img-name "57" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Goliáš", :year -1100, :time-desc "1100 př.n.l." :img-name "59" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "David", :year -1100, :time-desc "1100 př.n.l." :img-name "58" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Saulova nenávist", :year -1080, :time-desc "1080 př.n.l." :img-name "60" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "David králem", :year -1070, :time-desc "1070 př.n.l." :img-name "62" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Abigail", :year -1070, :time-desc "1070 př.n.l." :img-name "61" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Davidův hřích", :year -1050, :time-desc "1050 př.n.l." :img-name "63" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Šalomoun", :year -1035, :time-desc "1035 př.n.l." :img-name "64" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Postaven chrám", :year -1027, :time-desc "1027 př.n.l." :img-name "65" :scroll-text {:font-size "0.8em" :indent "9%" :top "-0.35em"}}
            {:title "Dělení království", :year -997, :time-desc "997 př.n.l." :img-name "66" :scroll-text {:font-size "0.8em" :indent "11%" :top "-0.35em"}}
            {:title "Elijáš", :year -940, :time-desc "940 př.n.l." :img-name "69" :scroll-text {:font-size "0.8em" :indent "11%" :top "-0.35em"}}
            {:title "Jezábel", :year -940, :time-desc "940 př.n.l." :img-name "67" :scroll-text {:font-size "0.8em" :indent "11%" :top "-0.35em"}}
            {:title "Jehošafat", :year -930, :time-desc "930 př.n.l." :img-name "68" :scroll-text {:font-size "0.8em" :indent "11%" :top "-0.35em"}}
            {:title "Izraleská dívenka", :year -900, :time-desc "900 př.n.l." :img-name "70" :scroll-text {:font-size "0.8em" :indent "11%" :top "-0.35em"}}
            {:title "Jonáš", :year -840, :time-desc "840 př.n.l." :img-name "71" :scroll-text {:font-size "0.8em" :indent "11%" :top "-0.35em"}}
            {:title "Ezekjáš", :year -740, :time-desc "740 př.n.l." :img-name "73" :scroll-text {:font-size "0.8em" :indent "11%" :top "-0.35em"}}
            {:title "Izajáš prorokuje", :year -700, :time-desc "700 př.n.l." :img-name "72" :scroll-text {:font-size "0.8em" :indent "11%" :top "-0.35em"}}
            {:title "Josijáš", :year -659, :time-desc "659-628 př.n.l." :img-name "74" :scroll-text {:font-size "0.7em" :indent "6%" :top "-0.45em"}}
            {:title "Jeremjáš", :year -640, :time-desc "640 př.n.l." :img-name "75" :scroll-text {:font-size "0.8em" :indent "11%" :top "-0.35em"}}
            {:title "V Babylóně", :year -617, :time-desc "617 př.n.l." :img-name "76" :scroll-text {:font-size "0.8em" :indent "11%" :top "-0.35em"}}
            {:title "Zničení Jeruzaléma", :year -607, :time-desc "607 př.n.l." :img-name "77" :scroll-text {:font-size "0.8em" :indent "11%" :top "-0.35em"}}
            {:title "Zlatá socha", :year -600, :time-desc "600 př.n.l." :img-name "78" :scroll-text {:font-size "0.8em" :indent "11%" :top "-0.35em"}}
            {:title "„MENE, MENE, ...“", :year -539, :time-desc "539 př.n.l." :img-name "79" :scroll-text {:font-size "0.8em" :indent "11%" :top "-0.35em"}}
            {:title "Daniel", :year -538, :time-desc "538 př.n.l." :img-name "80" :scroll-text {:font-size "0.8em" :indent "11%" :top "-0.35em"}}
            {:title "Návrat z Babylóna", :year -537, :time-desc "537 př.n.l." :img-name "81" :scroll-text {:font-size "0.8em" :indent "11%" :top "-0.35em"}}
            {:title "Ester", :year -480, :time-desc "480 př.n.l." :img-name "83" :scroll-text {:font-size "0.8em" :indent "11%" :top "-0.35em"}}
            {:title "Ezra", :year -460, :time-desc "460 př.n.l." :img-name "82" :scroll-text {:font-size "0.8em" :indent "11%" :top "-0.35em"}}
            {:title "Hradby Jeruzaléma", :year -455, :time-desc "455 př.n.l." :img-name "84" :scroll-text {:font-size "0.8em" :indent "11%" :top "-0.35em"}}
            {:title "Narození Ježíše", :year -2, :time-desc "2 př.n.l." :img-name "86" :scroll-text {:font-size "0.8em" :indent "15%" :top "-0.35em"}}
            {:title "Marie", :year -2, :time-desc "2 př.n.l." :img-name "85" :scroll-text {:font-size "0.8em" :indent "15%" :top "-0.35em"}}
            {:title "Astrologové", :year 1, :time-desc "1 n.l." :img-name "87" :scroll-text {:font-size "0.8em" :indent "20%" :top "-0.35em"}}
            {:title "Ježíšovi 12 let", :year 10, :time-desc "10 n.l." :img-name "88" :scroll-text {:font-size "0.8em" :indent "18%" :top "-0.35em"}}
            {:title "Křest Ježíše", :year 29, :time-desc "29 n.l." :img-name "89" :scroll-text {:font-size "0.8em" :indent "18%" :top "-0.35em"}}
            {:title "Samaritánka", :year 30, :time-desc "30 n.l." :img-name "91" :scroll-text {:font-size "0.8em" :indent "18%" :top "-0.35em"}}
            {:title "Očista chrámu", :year 30, :time-desc "30 n.l." :img-name "90" :scroll-text {:font-size "0.8em" :indent "18%" :top "-0.35em"}}
            {:title "Ježíš a děti", :year 31, :time-desc "31-33 n.l." :img-name "96" :scroll-text {:font-size "0.8em" :indent "14%" :top "-0.35em"}}
            {:title "Samaritán", :year 31, :time-desc "31-33 n.l." :img-name "97" :scroll-text {:font-size "0.8em" :indent "14%" :top "-0.35em"}}
            {:title "Nasycení tisíců lidí", :year 31, :time-desc "31-33 n.l." :img-name "95" :scroll-text {:font-size "0.8em" :indent "14%" :top "-0.35em"}}
            {:title "Jairova dcera", :year 31, :time-desc "31-33 n.l." :img-name "94" :scroll-text {:font-size "0.8em" :indent "14%" :top "-0.35em"}}
            {:title "Ježíš léčí", :year 31, :time-desc "31-33 n.l." :img-name "93" :scroll-text {:font-size "0.8em" :indent "14%" :top "-0.35em"}}
            {:title "Kázání na hoře", :year 31, :time-desc "31 n.l." :img-name "92" :scroll-text {:font-size "0.8em" :indent "18%" :top "-0.35em"}}
            {:title "Návrat do nebe", :year 33, :time-desc "33 n.l." :img-name "106" :scroll-text {:font-size "0.8em" :indent "18%" :top "-0.35em"}}
            {:title "Svatý duch", :year 33, :time-desc "33 n.l." :img-name "107" :scroll-text {:font-size "0.8em" :indent "18%" :top "-0.35em"}}
            {:title "Tomáš", :year 33, :time-desc "33 n.l." :img-name "105" :scroll-text {:font-size "0.8em" :indent "18%" :top "-0.35em"}}
            {:title "Ježíš vzkříšen", :year 33, :time-desc "33 n.l." :img-name "104" :scroll-text {:font-size "0.8em" :indent "18%" :top "-0.35em"}}
            {:title "Výkupní oběť", :year 33, :time-desc "33 n.l." :img-name "103" :scroll-text {:font-size "0.8em" :indent "18%" :top "-0.35em"}}
            {:title "Jidáš", :year 33, :time-desc "33 n.l." :img-name "102" :scroll-text {:font-size "0.8em" :indent "18%" :top "-0.35em"}}
            {:title "Pánova večeře", :year 33, :time-desc "33 n.l." :img-name "101" :scroll-text {:font-size "0.8em" :indent "18%" :top "-0.35em"}}
            {:title "„Kdy to bude?“", :year 33, :time-desc "33 n.l." :img-name "99" :scroll-text {:font-size "0.8em" :indent "18%" :top "-0.35em"}}
            {:title "Král na oslátku", :year 33, :time-desc "33 n.l." :img-name "98" :scroll-text {:font-size "0.8em" :indent "18%" :top "-0.35em"}}
            {:title "Propuštění z vězení", :year 33, :time-desc "33 n.l." :img-name "109" :scroll-text {:font-size "0.8em" :indent "18%" :top "-0.35em"}}
            {:title "Saul z Tarsu", :year 34, :time-desc "34 n.l." :img-name "111" :scroll-text {:font-size "0.8em" :indent "18%" :top "-0.35em"}}
            {:title "Štěpán", :year 34, :time-desc "34 n.l." :img-name "110" :scroll-text {:font-size "0.8em" :indent "18%" :top "-0.35em"}}
            {:title "Kornelius", :year 36, :time-desc "36 n.l." :img-name "112" :scroll-text {:font-size "0.8em" :indent "18%" :top "-0.35em"}}
            {:title "Pavel a Timoteus", :year 47, :time-desc "47-52 n.l." :img-name "113" :scroll-text {:font-size "0.8em" :indent "14%" :top "-0.35em"}}
            {:title "Eutychos", :year 52, :time-desc "52-56 n.l." :img-name "114" :scroll-text {:font-size "0.8em" :indent "14%" :top "-0.35em"}}
            {:title "Ztroskotání", :year 58, :time-desc "58-59 n.l." :img-name "116" :scroll-text {:font-size "0.8em" :indent "14%" :top "-0.35em"}}
            {:title "Pavel v Římě", :year 59, :time-desc "59-61 n.l." :img-name "117" :scroll-text {:font-size "0.8em" :indent "14%" :top "-0.35em"}}
            {:title "Zjevení Janovi", :year 96, :time-desc "96 n.l." :img-name "119" :scroll-text {:font-size "0.8em" :indent "14%" :top "-0.35em"}}
            {:title "„Nová země“", :year 9999, :time-desc "Již brzy..." :img-name "120" :scroll-text {:font-size "0.8em" :indent "12%" :top "-0.35em"}}])

(defn indexed-cards []
  (let [cards-with-id (map #(assoc %1 :id %2) cards (range))]
    (reduce #(assoc %1 (:id %2) %2) {} cards-with-id)))

(rf/reg-event-db
 :cards/reload
 (fn [db]
   (assoc db :cards (indexed-cards))))
