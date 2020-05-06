(ns timeline-game.events
  (:require [re-frame.core :as rf]))

(rf/reg-event-db
 :select-card
 (fn [db [_ id]]
   (assoc-in db [:player :selected-card-id] id)))

(rf/reg-event-db
 :deselect-card
 (fn [db]
   (assoc-in db [:player :selected-card-id] :nothing)))

(defn put-before [items pos item]
  (let [items (remove #{item} items)
        head (take pos items)
        tail (drop pos items)]
    (vec (concat head [item] tail))))

(defn remove-card [ids id]
  (remove #(= % id) ids))

(defn deactivate-player [db]
  (assoc-in db [:player :active?] false))

(defn activate-player [db]
  (assoc-in db [:player :active?] true))

(defn ordered? [xs]
  (or (empty? xs) (apply <= xs)))

(defn valid-placement? [timeline]
  (ordered? timeline))

(defn validate-card-placement [db id]
  (let [timeline (get-in db [:timeline :ids])
        valid? (valid-placement? timeline)]
    (assoc-in db [:timeline :status] {:id id :valid? valid? :active? true})))

(rf/reg-event-fx
 :place-card
 (fn [{:keys [db]} [_ pos]]
   (let [id (get-in db [:player :selected-card-id])]
     {:db (-> db
              (update-in [:timeline :ids] put-before pos id)
              (update-in [:player :hand] remove-card id)
              (validate-card-placement id)
              deactivate-player)
      :timeout [300 [:remove-misplaced-card id]]})))

(rf/reg-fx
 :timeout
 (fn [[time event]]
   (js/setTimeout #(rf/dispatch event) time)))

(rf/reg-event-db
 :remove-misplaced-card
 (fn [db [_ id]]
   (let [status (get-in db [:timeline :status])]
     (-> db
         (update-in [:timeline :ids] (fn [ids]
                                       (if (:valid? status)
                                         ids
                                         (remove-card ids id))))
         (assoc-in [:timeline :status :active?] false)
         activate-player))))
