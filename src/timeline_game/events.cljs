(ns timeline-game.events
  (:require [re-frame.core :as rf]))

(rf/reg-fx
 :timeout
 (fn [[time event]]
   (js/setTimeout #(rf/dispatch event) time)))

(rf/reg-fx
 :dispatch
 (fn [event]
   (rf/dispatch event)))

(rf/reg-event-db
 :select-card
 (fn [db [_ id]]
   (assoc-in db [:user-action :selected-card-id] id)))

(rf/reg-event-db
 :clear-card-selection
 (fn [db]
   (assoc-in db [:user-action :selected-card-id] :nothing)))

(defn put-before [items pos item]
  (let [items (remove #{item} items)
        head (take pos items)
        tail (drop pos items)]
    (vec (concat head [item] tail))))

(defn remove-card [ids id]
  (remove #(= % id) ids))

(rf/reg-event-db
 :place-card
 (fn [db [_ pos]]
   (let [id (get-in db [:user-action :selected-card-id])]
     (-> db
         (update-in [:timeline :ids] put-before pos id)
         (update-in [:hand] remove-card id)))))

(defn ordered? [xs]
  (or (empty? xs) (apply <= xs)))

(rf/reg-event-fx
 :validate-card-placement
 (fn [{:keys [db]} _]
   (let [timeline (get-in db [:timeline :ids])
         valid? (ordered? timeline)
         id (get-in db [:user-action :selected-card-id])]
     {:db (assoc-in db [:timeline :status] {:id id :valid? valid? :active? true})
      :timeout [300 [:remove-misplaced-card id valid?]]})))

(rf/reg-event-fx
 :remove-misplaced-card
 (fn [{:keys [db]} [_ id valid?]]
   {:db (-> db
            (update-in [:timeline :ids] (fn [ids]
                                          (cond
                                            (not valid?)
                                            (remove-card ids id)

                                            :else
                                            ids)))
            (update-in [:timeline :status] update :active? not))
    :dispatch [:activate-player]}))

(rf/reg-event-db
 :disable-player
 (fn [db]
   (assoc-in db [:player :active?] false)))

(rf/reg-event-db
 :activate-player
 (fn [db]
   (assoc-in db [:player :active?] true)))