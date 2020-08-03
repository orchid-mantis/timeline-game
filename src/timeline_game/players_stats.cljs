(ns timeline-game.players-stats
  (:require [re-frame.core :as rf]))

(defn players-stats [cards history]
  (let [ids (:ids history)
        validity (vals (:validity history))]
    {:valid-count (count (filter true? validity))
     :invalid-count (count (filter false? validity))
     :last-played-card (cards (first ids))}))

(rf/reg-sub
 :hand/size
 (fn [[_ player]]
   (rf/subscribe [:hand/ids player]))
 (fn [hand-ids]
   (count hand-ids)))

(rf/reg-sub
 :player/stats
 (fn [[_ player]]
   [(rf/subscribe [:cards])
    (rf/subscribe [:hand/size player])
    (rf/subscribe [:history player])])
 (fn [[cards hand-size history]]
   (merge {:hand-size hand-size}
          (players-stats cards history))))

(defn view [player]
  (let [stats (rf/subscribe [:player/stats player])]
    (fn []
      (let [valid-count (:valid-count @stats)
            invalid-count (:invalid-count @stats)
            hand-size (:hand-size @stats)
            last-played-card (:last-played-card @stats)]
        [:div.column.row-data
         [:span.item {:title "Well-played count"}
          [:i.fas.fa-check {:style {:color :green}}]
          [:span valid-count]]

         [:span.item {:title "Wrong-played count"}
          [:i.fas.fa-times {:style {:color :red}}]
          [:span invalid-count]]

         [:span.item {:title "Number of cards in hand"}
          [:i.fas.fa-clone {:style {:color :black}}]
          [:span hand-size]]

         [:span.item {:title "Card title"}
          [:i.fas.fa-caret-right {:style {:color :black}}]
          [:span
           (get last-played-card :title "n/a")]]]))))
