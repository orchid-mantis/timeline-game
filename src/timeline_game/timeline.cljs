(ns timeline-game.timeline
  (:require [re-frame.core :as rf]
            [reagent.core :as reagent]))

(rf/reg-sub
 :timeline
 (fn [db]
   (:timeline db)))

(rf/reg-sub
 :timeline/cards
 (fn []
   [(rf/subscribe [:cards])
    (rf/subscribe [:timeline])])
 (fn [[cards timeline]]
   (vec (map cards timeline))))

(defn drop-zone [s pos]
  [:li {:key pos
        :on-drag-over (fn [e]
                        (.preventDefault e))
        :on-drag-enter (fn [e]
                         (.preventDefault e)
                         (swap! s assoc-in [:drag-enter pos] true))
        :on-drag-leave (fn []
                         (swap! s update-in [:drag-enter pos] (fn [] false)))
        :on-drop (fn [e]
                   (.preventDefault e)
                   (rf/dispatch [:place-card (/ pos 2)])
                   (swap! s update-in [:drag-enter pos] (fn [] false)))

        :style {:display :table-cell
                :text-align :center
                :background-color (when (get-in @s [:drag-enter pos]) :yellow)
                :width 20}}
   "*"])

(defn cards-in-timeline []
  (let [s (reagent/atom {})]
    (fn []
      (let [cards @(rf/subscribe [:timeline/cards])
            items (concat [:drop-zone] (interpose :drop-zone cards) [:drop-zone])]
        [:div
         [:ul
          (doall
           (for [[item pos] (map vector items (range))]
             (if (= item :drop-zone)
               (drop-zone s pos)

               [:li {:key pos
                     :style {:display :table-cell
                             :padding 5
                             :width 100
                             :border "2px solid blue"}}
                (:title item)])))]
         ;[:p (pr-str @s)]
         ]))))
