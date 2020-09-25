(ns timeline-game.ui.timeline.events
  (:require
   [re-frame.core :as rf]
   [timeline-game.common :refer [put-before remove-card]]))

(rf/reg-event-fx
 :place-card
 (fn [{:keys [db]} [_ id pos]]
   {:db (-> db
            (update-in [:player :hand :ids] remove-card id)
            (update-in [:timeline :ids] put-before pos id)
            (assoc-in [:player :selected-cards] #{}))
    :dispatch [:eval-move :player id]}))

(rf/reg-event-fx
 :scroll-timeline
 (fn [{:keys [db]} [_ delta]]
   (let [node (get-in db [:dom-nodes :timeline])
         turn (get-in db [:game :turn])
         player (get-in db [:game :player])
         game-result (get-in db [:game :result])
         players-turn? (and (= player :player) (= turn :ready))
         game-ended? (not= game-result :await)
         allow-scroll? (or game-ended? players-turn?)]
     (merge {:db db}
            (when allow-scroll?
              {:apply-scroll-timeline [node delta]})))))

(defn horizontal-scroll [node delta]
  (when node
    (set! (.-scrollLeft node) (+ (.-scrollLeft node) delta))))

(rf/reg-fx
 :apply-scroll-timeline
 (fn [[node delta]]
   (horizontal-scroll node delta)))