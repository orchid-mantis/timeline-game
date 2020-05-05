(ns timeline-game.events
  (:require [re-frame.core :as rf]))

(rf/reg-fx
 :timeout
 (fn [[time event]]
   (js/setTimeout #(rf/dispatch event) time)))

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

(rf/reg-event-db
 :place-card
 (fn [db [_ pos]]
   (let [id (get-in db [:user-action :selected-card-id])]
     (-> db
         (update-in [:timeline :ids] put-before pos id)
         (update-in [:hand] (fn [ids] (remove #(= % id) ids)))))))

(defn ordered? [xs]
  (or (empty? xs) (apply <= xs)))

(rf/reg-event-fx
 :validate-card-position
 (fn [{:keys [db]} _]
   (let [timeline (get-in db [:timeline :ids])
         valid? (ordered? timeline)
         id (get-in db [:user-action :selected-card-id])]
     {:db (assoc-in db [:timeline :status] {:id id :valid-position? valid?})
      :timeout [1000 [:remove-misplaced-card id valid?]]})))

(defn remove-card [ids id]
  (remove #(= % id) ids))

(rf/reg-event-db
 :remove-misplaced-card
 (fn [db [_ id valid?]]
   (-> db
       (update-in [:timeline :ids] (fn [ids]
                                     (cond
                                       (not valid?)
                                       (remove-card ids id)

                                       :else
                                       ids)))
       (assoc-in [:timeline :status] {}))))
