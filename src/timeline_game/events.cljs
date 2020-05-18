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
      :timeout [300 [:finish-place-card id]]})))

(rf/reg-fx
 :timeout
 (fn [[time event]]
   (js/setTimeout #(rf/dispatch event) time)))

(defn draw-card [db]
  (let [card-id (first (:deck db))]
    (if card-id
      (-> db
          (update-in [:player :hand] conj card-id)
          (update :deck #(drop 1 %)))
      db)))

(defn eval-turn [hand error-count]
  (cond
    (> error-count 3) [false :player-lost]
    (empty? hand) [false :player-won]
    :else [true nil]))

(defn select-card [hand]
  (let [card-id (first (shuffle hand))]
    [card-id (remove-card hand card-id)]))

(defn bot-place-card [timeline card-id]
  (cond
    (nil? card-id) timeline
    :else (let [[lesser greater] (split-with #(> card-id %) timeline)]
            (vec (concat lesser [card-id] greater)))))

(defn bot-play-turn [db]
  (let [hand (get-in db [:bot :hand])
        [card-id new-hand] (select-card hand)]
    (-> db
        (assoc-in [:bot :hand] new-hand)
        (update-in [:timeline :ids] bot-place-card card-id))))

(rf/reg-event-db
 :finish-place-card
 (fn [db [_ id]]
   (let [status (get-in db [:timeline :status])
         valid-placement? (:valid? status)]
     (-> db
         ((fn [db]
            (if valid-placement?
              db
              (-> db
                  (update-in [:timeline :ids] #(remove-card % id))
                  (update-in [:player :error-count] (fnil inc 0))
                  (draw-card)))))
         (update-in [:player :history :ids] conj id)
         (assoc-in [:player :history :validity id] valid-placement?)
         (assoc-in [:timeline :status :active?] false)
         (bot-play-turn)
         ((fn [db]
            (let [hand (get-in db [:player :hand])
                  error-count (get-in db [:player :error-count])
                  [next-round? game-result] (eval-turn hand error-count)]
              (if next-round?
                (activate-player db)
                (assoc-in db [:game :result] game-result)))))))))
