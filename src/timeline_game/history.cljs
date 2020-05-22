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
    [:ul
     (doall
      (for [card @history-cards-sub
            :let [id (:id card)
                  valid? (:valid? card)]]
        [:li
         {:key id
          :style {:margin 5
                  :padding 5
                  :width 100
                  :border (if valid? "2px solid green" "2px solid red")
                  :list-style-type :none}}
         (:title card)]))]))
