(ns timeline-game.players-stats
  (:require [re-frame.core :as rf]))

(defn history-overview [cards history]
  (let [ids (:ids history)
        validity (vals (:validity history))]
    {:valid-count (count (filter true? validity))
     :invalid-count (count (filter false? validity))
     :last-played-card (cards (first ids))}))

(rf/reg-sub
 :player/stats
 (fn [[_ player]]
   [(rf/subscribe [:cards])
    (rf/subscribe [:history player])])
 (fn [[cards history]]
   (history-overview cards history)))

(defn view [player]
  (let [stats (rf/subscribe [:player/stats player])]
    (fn []
      (let [valid-count (:valid-count @stats)
            invalid-count (:invalid-count @stats)
            last-played-card (:last-played-card @stats)]
        [:div.hist-overview.column
         [:span.item
          [:i.fas.fa-check {:style {:color :green}}]
          [:span valid-count]]

         [:span.item
          [:i.fas.fa-times {:style {:color :red}}]
          [:span invalid-count]]

         [:span.item
          [:i.fas.fa-clone {:style {:color :black}}]
          [:span (get last-played-card :title "---")]]]))))
