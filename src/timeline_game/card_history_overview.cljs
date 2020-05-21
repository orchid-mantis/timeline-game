(ns timeline-game.card_history_overview
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf]))

(rf/reg-sub
 :history/overview
 (fn []
   [(rf/subscribe [:cards])
    (rf/subscribe [:history])])
 (fn [[cards history]]
   (let [ids (:ids history)
         validity (vals (:validity history))]
     {:valid-count (count (filter true? validity))
      :invalid-count (count (filter false? validity))
      :last-played-card (cards (first ids))})))

(defn view []
  (let [overview (rf/subscribe [:history/overview])]
    (fn []
      (let [valid-count (:valid-count @overview)
            invalid-count (:invalid-count @overview)
            last-played-card (:last-played-card @overview)]
        [:div.hist-overview
         [:i.fas.fa-check {:style {:color :green}}]
         [:span valid-count]

         [:i.fas.fa-times {:style {:color :red}}]
         [:span invalid-count]

         [:i.fas.fa-clone {:style {:color :black}}]
         [:span (get last-played-card :title "---")]]))))
