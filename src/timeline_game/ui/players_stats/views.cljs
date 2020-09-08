(ns timeline-game.ui.players-stats.views
  (:require
   [re-frame.core :as rf]
   [timeline-game.ui.players-stats.subs]))

(defn view [player]
  (let [stats (rf/subscribe [:player/stats-view player])
        hand-size (rf/subscribe [:hand/size player])]
    (fn []
      (let [last-played-card (:last-played-card @stats)]
        [:div.column.row-data
         [:div {:title "Well-played count"}
          [:i.fas.fa-check {:style {:color :green}}]
          [:span (:well-played-count @stats)]]

         [:div {:title "Wrong-played count"}
          [:i.fas.fa-times {:style {:color :red}}]
          [:span (:wrong-played-count @stats)]]

         [:div {:title "Number of cards in hand"}
          [:i.fas.fa-clone {:style {:color :black}}]
          [:span @hand-size]]

         [:div {:title "Card title"}
          [:i.fas.fa-caret-right {:style {:color :black}}]
          [:span
           (get last-played-card :title "n/a")]]]))))
