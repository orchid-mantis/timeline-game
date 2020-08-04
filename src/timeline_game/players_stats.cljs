(ns timeline-game.players-stats
  (:require [re-frame.core :as rf]))

(defn players-stats [cards history]
  (let [played-cards (vals history)
        players-cards-count (count played-cards)
        well-played-count (count (filter #(true? (:valid? %)) played-cards))]
    {:well-played-count well-played-count
     :wrong-played-count (- players-cards-count well-played-count)
     :last-played-card (cards (:id (last played-cards)))}))

(rf/reg-sub
 :hand/size
 (fn [[_ player]]
   (rf/subscribe [:hand/ids player]))
 (fn [hand-ids]
   (count hand-ids)))

(defn map-values [f map]
  (into {} (for [[k v] map]
             [k (f v)])))

(rf/reg-sub
 :history/player
 (fn [db [_ player]]
   (let [history (get-in db [:history])]
     (map-values #(player %) history))))

(rf/reg-sub
 :player/stats
 (fn [[_ player]]
   [(rf/subscribe [:cards])
    (rf/subscribe [:hand/size player])
    (rf/subscribe [:history/player player])])
 (fn [[cards hand-size history]]
   (merge {:hand-size hand-size}
          (players-stats cards history))))

(defn view [player]
  (let [stats (rf/subscribe [:player/stats player])]
    (fn []
      (let [last-played-card (:last-played-card @stats)]
        [:div.column.row-data
         [:span.item {:title "Well-played count"}
          [:i.fas.fa-check {:style {:color :green}}]
          [:span (:well-played-count @stats)]]

         [:span.item {:title "Wrong-played count"}
          [:i.fas.fa-times {:style {:color :red}}]
          [:span (:wrong-played-count @stats)]]

         [:span.item {:title "Number of cards in hand"}
          [:i.fas.fa-clone {:style {:color :black}}]
          [:span (:hand-size @stats)]]

         [:span.item {:title "Card title"}
          [:i.fas.fa-caret-right {:style {:color :black}}]
          [:span
           (get last-played-card :title "n/a")]]]))))
