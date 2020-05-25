(ns timeline-game.events
  (:require [re-frame.core :as rf]
            [clojure.set :as set]))

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
              (validate-card-placement id))
      :dispatch [:next-player]
      :timeout [300 [:eval-player-turn id]]})))

(rf/reg-fx
 :timeout
 (fn [[time event]]
   (js/setTimeout #(rf/dispatch event) time)))

(defn draw-card [db player]
  (let [card-id (first (:deck db))]
    (if card-id
      (-> db
          (update-in [player :hand] conj card-id)
          (update :deck #(drop 1 %)))
      db)))

(defn historize [db player card-id valid-placement?]
  (-> db
      (update-in [player :history :ids] conj card-id)
      (assoc-in [player :history :validity card-id] valid-placement?)))

(defn handle-card-placement [db player id]
  (let [status (get-in db [:timeline :status])
        valid-placement? (:valid? status)]
    (-> db
        (historize player id valid-placement?)
        ((fn [db]
           (if valid-placement?
             db
             (-> db
                 (update-in [:timeline :ids] #(remove-card % id))
                 (update-in [player :error-count] (fnil inc 0))
                 (draw-card player))))))))

(defn hide-last-status [db]
  (assoc-in db [:timeline :status :active?] false))

(rf/reg-event-fx
 :eval-player-turn
 (fn [{:keys [db]} [_ id]]
   {:db (-> db
            (handle-card-placement :player id)
            hide-last-status)
    :dispatch [:play-bot-turn]}))

(defn select-card [hand]
  (let [card-id (first (shuffle hand))]
    [card-id (remove-card hand card-id)]))

(defn place-card-correct [timeline card-id]
  (let [[lesser greater] (split-with #(> card-id %) timeline)]
    (vec (concat lesser [card-id] greater))))

(defn find-index [needle haystack]
  (first (keep-indexed #(when (= %2 needle) %1) haystack)))

(defn place-card-wrong [timeline card-id]
  (let [correct-timeline (place-card-correct timeline card-id)
        correct-pos (find-index card-id correct-timeline)
        all-pos (range (count correct-timeline))
        all-wrong-pos (into [] (set/difference (set all-pos) #{correct-pos}))
        random-pos (rand-nth all-wrong-pos)]
    (put-before timeline random-pos card-id)))

(defn bot-place-card [timeline card-id bot-success?]
  (cond
    (nil? card-id) timeline
    bot-success? (place-card-correct timeline card-id)
    :else (place-card-wrong timeline card-id)))

(rf/reg-event-fx
 :play-bot-turn
 (fn [{:keys [db]}]
   (let [[card-id new-hand] (select-card (get-in db [:bot :hand]))
         distribution (get-in db [:bot :success-dist])]
     {:db (-> db
              (assoc-in [:bot :hand] new-hand)
              (update-in [:timeline :ids] bot-place-card card-id (peek distribution))
              (assoc-in [:bot :success-dist] (pop distribution))
              (validate-card-placement card-id))
      :timeout [500 [:eval-bot-turn card-id]]})))

(rf/reg-event-fx
 :eval-bot-turn
 (fn [{:keys [db]} [_ id]]
   {:db (-> db
            (handle-card-placement :bot id)
            hide-last-status)
    :dispatch [:evaluate-round]}))

(defn evaluate-round [[player-hand bot-hand]]
  (cond
    (and (empty? player-hand) (empty? bot-hand)) [false :tie]
    (empty? player-hand) [false :player-won]
    (empty? bot-hand) [false :player-lost]
    :else [true nil]))

(rf/reg-event-fx
 :evaluate-round
 (fn [{:keys [db]} _]
   (let [players-hands [(get-in db [:player :hand]) (get-in db [:bot :hand])]
         [next-round? game-result] (evaluate-round players-hands)]
     {:db db
      :dispatch (if next-round?
                  [:next-round]
                  [:game-end game-result])})))
