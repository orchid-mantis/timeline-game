(ns btg.app.ui.timeline.events
  (:require
   [re-frame.core :as rf]
   [btg.app.common :refer [put-before remove-card]]))

(rf/reg-event-fx
 :timeline/update-scrollable
 (fn [{:keys [db]} _]
   (let [node (get-in db [:dom-nodes :timeline])
         old-value (get-in db [:timeline :scrollable?])]
     {:db db
      :update-scrollable [node old-value]})))

(rf/reg-fx
 :update-scrollable
 (fn [[node old-value]]
   (when node
     (let [new-value (- (.-scrollWidth node) (.-clientWidth node))
           scrollable? (not (zero? new-value))]
       (when (not= scrollable? old-value)
         (rf/dispatch [:scrollable-changed scrollable?]))))))

(rf/reg-event-db
 :scrollable-changed
 (fn [db [_ value]]
   (assoc-in db [:timeline :scrollable?] value)))

(rf/reg-event-fx
 :user/place-card
 (fn [{:keys [db]} [_ id pos]]
   {:db (-> db
            (update-in [:player :hand :ids] remove-card id)
            (update-in [:timeline :ids] put-before pos id)
            (assoc-in [:player :selected-cards] #{}))
    :dispatch [:eval-move :player id]}))

(rf/reg-event-fx
 :user/scroll-timeline
 (fn [{:keys [db]} [_ delta]]
   (let [node (get-in db [:dom-nodes :timeline])
         turn (get-in db [:game :turn])
         player (get-in db [:game :curr-player])
         game-result (get-in db [:game :result])
         game-ended? (not= game-result :await)
         allow-scroll? (or (and (= player :player) (= turn :ready))
                           game-ended?)]
     (merge {:db db}
            (when allow-scroll?
              {:scroll-timeline [node delta]})))))
