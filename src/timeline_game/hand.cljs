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
 :hand/state
 (fn [db _]
   (get-in db [:player :hand :state])))

(rf/reg-sub
 :hand/last-added
 (fn [db]
   (get-in db [:player :hand :last-added-id])))

(rf/reg-sub
 :drawn-card-id
 (fn []
   [(rf/subscribe [:hand/state])
    (rf/subscribe [:hand/last-added])])
 (fn [[hand-state last-added-id]]
   (if (= hand-state :draw-card-animation)
     last-added-id
     nil)))

(rf/reg-sub
 :hand/ids
 (fn [db [_ player]]
   (get-in db [player :hand :ids])))

(rf/reg-sub
 :hand/cards
 (fn []
   [(rf/subscribe [:cards])
    (rf/subscribe [:hand/ids :player])])
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
        cards (rf/subscribe [:hand/cards])
        drawn-card-id (rf/subscribe [:drawn-card-id])]
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
            (ui/cs (when @player-turn? :selectable)
                   (when (= id @drawn-card-id) :slide-in-top))
            {:user-select (when (not @player-turn?) :none)
             :cursor (if @player-turn? :pointer :not-allowed)
             :opacity (when (not @player-turn?) 0.3)}]]))])))
