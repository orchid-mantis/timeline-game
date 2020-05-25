(ns timeline-game.history
  (:require [re-frame.core :as rf]
            [reagent.core :as reagent]))

(rf/reg-sub
 :history
 (fn [db [_ player]]
   (get-in db [player :history])))

(defn played-cards [cards history]
  (let [ids (:ids history)
        validity (:validity history)]
    (map (fn [id]
           (let [valid? (validity id)
                 card (cards id)]
             (assoc card :valid? valid?)))
         ids)))

(rf/reg-sub
 :history/played-cards
 (fn [[_ player]]
   [(rf/subscribe [:cards])
    (rf/subscribe [:history player])])
 (fn [[cards history]]
   (played-cards cards history)))

(defn view [history-cards-sub]
  (fn []
    (let [cards @history-cards-sub
          cards-count (count cards)]
      (if (empty? cards)
        [:p "No card was played yet."]
        [:table
         [:thead
          [:tr
           [:th "Round"]
           [:th "Card"]]]
         [:tbody
          (doall
           (for [[card index] (zipmap cards (range))
                 :let [index (- cards-count index)
                       id (:id card)
                       valid? (:valid? card)]]
             [:tr {:key id}
              [:td {:style {:text-align :center
                            :vertical-align :middle}}
               index]
              [:td {:style {:text-align :center
                            :margin 5
                            :padding 5
                            :width 100
                            :border (if valid? "2px solid green" "2px solid red")
                            :list-style-type :none}}
               (:title card)]]))]]))))
