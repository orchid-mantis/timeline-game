(ns timeline-game.events
  (:require [re-frame.core :as rf]))

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
