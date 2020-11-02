(ns timeline-game.ui.card-set.views
  (:require
   [re-frame.core :as rf]
   [reagent.core :as reagent]
   [timeline-game.ui.components :as ui]))

(rf/reg-sub
 :card-set/ordered
 (fn []
   (rf/subscribe [:cards]))
 (fn [cards]
   (sort-by :year <= (vals cards))))

(defn view []
  (let [timer (atom nil)
        game-card (rf/subscribe [:game-card/comp])
        cards (rf/subscribe [:card-set/ordered])]
    (reagent/create-class
     {:reagent-render
      (fn []
        [:div
         (doall
          (for [card @cards]
            ^{:key (:id card)}
            [@game-card card true]))])

      :component-did-mount
      (fn []
        (reset! timer (js/setInterval
                       #(rf/dispatch [:cards/reload])
                       2000)))

      :component-will-unmount
      (fn []
        (js/clearInterval @timer))})))
