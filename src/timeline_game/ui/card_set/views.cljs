(ns timeline-game.ui.card-set.views
  (:require
   [re-frame.core :as rf]
   [timeline-game.ui.components :as ui]))

(rf/reg-sub
 :card-set/ordered
 (fn []
   (rf/subscribe [:cards]))
 (fn [cards]
   (sort-by :year <= (vals cards))))

(defn view []
  (let [cards (rf/subscribe [:card-set/ordered])]
    [:div
     (doall
      (for [card @cards]
        ^{:key (:id card)}
        [ui/game-card card true]))]))
