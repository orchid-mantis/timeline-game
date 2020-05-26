(ns timeline-game.timeline
  (:require [re-frame.core :as rf]
            [reagent.core :as reagent]))

(rf/reg-sub
 :timeline-ids
 (fn [db]
   (get-in db [:timeline :ids])))

(rf/reg-sub
 :timeline/last-added
 (fn [db]
   (get-in db [:timeline :last-added-id])))

(rf/reg-sub
 :timeline/cards
 (fn []
   [(rf/subscribe [:cards])
    (rf/subscribe [:timeline-ids])])
 (fn [[cards timeline]]
   (vec (map cards timeline))))

(rf/reg-sub
 :highlight-color
 (fn []
   (rf/subscribe [:turn]))
 (fn [turn]
   (case turn
     :well-placed-card :green
     :misplaced-card :red
     nil)))

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

(defn view []
  (let [cards (rf/subscribe [:timeline/cards])
        last-added-id (rf/subscribe [:timeline/last-added])
        color (rf/subscribe [:highlight-color])
        s (reagent/atom {})]
    (fn []
      (let [items (concat [:drop-zone] (interpose :drop-zone @cards) [:drop-zone])]
        [:div
         [:ul
          (doall
           (for [[item pos] (map vector items (range))
                 :let [id (:id item)]]
             (if (= item :drop-zone)
               (drop-zone s pos)

               [:li {:key pos
                     :style {:display :table-cell
                             :padding 5
                             :width 100
                             :border "2px solid blue"
                             :background-color (when (= id @last-added-id) @color)}}
                (:title item)])))]
         ;[:p (pr-str @s)]
         ]))))
