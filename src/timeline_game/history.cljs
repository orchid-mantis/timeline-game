(ns timeline-game.history
  (:require [re-frame.core :as rf]
            [reagent.core :as reagent]))

(rf/reg-sub
 :history
 (fn [db]
   (get-in db [:player :history])))

(rf/reg-sub
 :history/cards
 (fn []
   [(rf/subscribe [:cards])
    (rf/subscribe [:history])])
 (fn [[cards history]]
   (let [ids (:ids history)
         validity (:validity history)]
     (map (fn [id]
            (let [valid? (validity id)
                  card (cards id)]
              (assoc card :valid? valid?)))
          ids))))

(defn view []
  (let [cards (rf/subscribe [:history/cards])]
    (fn []
      [:ul
       (doall
        (for [card @cards
              :let [id (:id card)
                    valid? (:valid? card)]]
          [:li
           {:key id
            :style {:margin 5
                    :padding 5
                    :width 100
                    :border (if valid? "2px solid green" "2px solid red")
                    :list-style-type :none}}
           (:title card)]))])))
