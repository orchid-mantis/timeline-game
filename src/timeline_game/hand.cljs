(ns timeline-game.hand
  (:require [re-frame.core :as rf]
            [timeline-game.basic-card :as basic-card]
            [timeline-game.ui-helpers :as ui]))

;; -- Subscriptions -----------------------------------------------------------

(rf/reg-sub
 :players-turn?
 (fn []
   [(rf/subscribe [:player])
    (rf/subscribe [:turn])])
 (fn [[player turn]]
   (and (= player :player)
        (= turn :ready))))

(rf/reg-sub
 :hand/ids
 (fn [db]
   (get-in db [:player :hand :ids])))

(rf/reg-sub
 :hand/cards
 (fn []
   [(rf/subscribe [:cards])
    (rf/subscribe [:hand/ids])])
 (fn [[cards hand]]
   (map cards hand)))

;; -- Events ------------------------------------------------------------------

(rf/reg-event-db
 :select-card
 (fn [db [_ id]]
   (assoc-in db [:player :selected-card-id] id)))

(rf/reg-event-db
 :deselect-card
 (fn [db]
   (assoc-in db [:player :selected-card-id] :nothing)))

;; -- UI ------------------------------------------------------------------

(defn view []
  (let [player-turn? (rf/subscribe [:players-turn?])
        cards (rf/subscribe [:hand/cards])]
    (fn []
      [:ul
       (doall
        (for [card @cards
              :let [id (:id card)]]
          [:li
           {:key id
            :draggable (when @player-turn? true)
            :on-drag-start #(rf/dispatch [:select-card id])
            :on-drag-end (fn []
                           (rf/dispatch [:deselect-card]))
            :style {:display :inline-block}}
           [basic-card/view
            card
            false
            (ui/cs (when @player-turn? :selectable))
            {:user-select (when (not @player-turn?) :none)
             :cursor (if @player-turn? :pointer :not-allowed)
             :opacity (when (not @player-turn?) 0.3)}]]))])))
