(ns timeline-game.card-set
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf]
            [timeline-game.basic-card :as basic-card]))

(rf/reg-sub
 :card-set/ordered
 (fn []
   (rf/subscribe [:cards]))
 (fn [cards]
   (sort-by :year <= (vals cards))))

(defn view []
  (let [cards (rf/subscribe [:card-set/ordered])]
    (fn []
      [:div
       [:ul
        (doall
         (for [[item pos] (map vector @cards (range))
               :let [id (:id item)]]
           [:li {:id id
                 :key pos
                 :style {:display :inline-block}}
            [basic-card/view item true]]))]])))