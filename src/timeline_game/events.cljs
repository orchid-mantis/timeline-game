(ns timeline-game.events
  (:require [re-frame.core :as rf]
            [timeline-game.bot-player]
            [timeline-game.common :refer [remove-card ordered?]]
            [timeline-game.fsm :as fsm]))

(def keep-state-in-ms 700)

(rf/reg-event-fx
 :init-turn
 (fn [{:keys [db]} [event _]]
   (let [player (get-in db [:game :player])]
     (merge {:db (fsm/update-turn db event)}
            (when (= player :bot) {:dispatch [:play-bot-move]})))))

(rf/reg-fx
 :timeout
 (fn [[time event]]
   (js/setTimeout #(rf/dispatch event) time)))

(defn update-stats [stats card-id valid-move?]
  (let [count-key (if valid-move? :well-played-count :wrong-played-count)]
    (-> stats
        (update count-key inc)
        (assoc :last-played-card-id card-id)
        (assoc :last-valid-move? valid-move?))))

(defn historize [db player card-id valid-move?]
  (let [round-num (get-in db [:game :round])]
    (-> db
        (assoc-in [:history round-num player] {:id card-id :valid? valid-move?})
        (update-in [player :stats] update-stats card-id valid-move?))))

(rf/reg-event-fx
 :eval-move
 (fn [{:keys [db]} [_ player id]]
   (let [ids (get-in db [:timeline :ids])
         cards (:cards db)
         valid-move? (ordered? ids cards)]
     {:db (-> db
              (assoc-in [:timeline :last-added-id] id)
              (historize player id valid-move?))
      :dispatch (if valid-move?
                  [:correct-move]
                  [:wrong-move player id])})))

(rf/reg-event-fx
 :correct-move
 (fn [{:keys [db]} [event _]]
   {:db (fsm/update-turn db event)
    :timeout [keep-state-in-ms [:correct-end-turn]]}))

(rf/reg-event-fx
 :correct-end-turn
 (fn [{:keys [db]} [event _]]
   {:db (fsm/update-turn db event)
    :dispatch [:end-turn]}))

(rf/reg-event-fx
 :wrong-move
 (fn [{:keys [db]} [event player id]]
   {:db (fsm/update-turn db event)
    :timeout [keep-state-in-ms [:wrong-end-turn player id]]}))

(rf/reg-event-fx
 :wrong-end-turn
 (fn [{:keys [db]} [event player id]]
   (let [mode (get-in db [:game :mode])]
     {:db (-> db
              (update-in [:timeline :ids] #(remove-card % id))
              (update :deck #(conj % id))
              (fsm/update-turn event))
      :dispatch (if (= mode :standard)
                  [:draw-card [player] :end-turn]
                  [:end-turn])})))

(defn draw-card [db player]
  (let [card-id (first (:deck db))]
    (if card-id
      (-> db
          (update-in [player :hand :ids] conj card-id)
          (update :deck #(vec (drop 1 %)))
          (assoc-in [player :hand :last-added-id] card-id))
      db)))

(defn players-draw-card [db players]
  (reduce #(draw-card %1 %2) db players))

(defn fsm-update-hands [db players event]
  (reduce #(fsm/update-hand %1 event %2) db players))

(rf/reg-event-fx
 :draw-card
 (fn [{:keys [db]} [event players next-event]]
   {:db (-> db
            (fsm-update-hands players event)
            (players-draw-card players))
    :timeout [keep-state-in-ms [:end-draw-card players next-event]]}))

(rf/reg-event-fx
 :end-draw-card
 (fn [{:keys [db]} [event players next-event]]
   {:db (fsm-update-hands db players event)
    :dispatch [next-event]}))

(rf/reg-event-fx
 :end-turn
 (fn [{:keys [db]} _]
   (let [next-player (fsm/next-player db :next-player)]
     {:db db
      :dispatch (if (= next-player :none)
                  [:eval-round]
                  [:next-player])})))

(rf/reg-event-fx
 :next-player
 (fn [{:keys [db]} [event _]]
   {:db (fsm/update-player db event)
    :dispatch [:init-turn]}))

(defn eval-standard [db]
  (let [player-hand (get-in db [:player :hand :ids])
        bot-hand (get-in db [:bot :hand :ids])]
    (cond
      (and (empty? player-hand) (empty? bot-hand)) [true :sudden-death]
      (empty? player-hand) [false :player-won]
      (empty? bot-hand)    [false :player-lost]
      :else                [true :standard])))

(defn well-played? [last-round player]
  (:valid? (player last-round)))

(defn eval-sudden-death [db]
  (let [round-num (get-in db [:game :round])
        history (get-in db [:history])
        last-round (get history round-num)
        player-well-played? (well-played? last-round :player)
        bot-well-played? (well-played? last-round :bot)]
    (cond
      (and player-well-played? bot-well-played?) [true :sudden-death]
      player-well-played? [false :player-won]
      bot-well-played?    [false :player-lost]
      :else               [true :sudden-death])))

(defn evaluate-round [db]
  (let [mode (get-in db [:game :mode])]
    (case mode
      :standard (eval-standard db)
      :sudden-death (eval-sudden-death db))))

(rf/reg-event-fx
 :eval-round
 (fn [{:keys [db]} _]
   (let [[next-round? result] (evaluate-round db)]
     {:db db
      :dispatch (if next-round?
                  [:next-round result]
                  [:game-end result])})))

(rf/reg-event-fx
 :next-round
 (fn [{:keys [db]} [_ mode]]
   {:db (-> db
            (update-in [:game :round] inc)
            (assoc-in [:game :player] nil)
            (assoc-in [:game :mode] mode))
    :dispatch (if (= mode :sudden-death)
                [:draw-card [:player :bot] :next-player]
                [:next-player])}))

(rf/reg-event-db
 :game-end
 (fn [db [_ game-result]]
   (-> db
       (assoc-in [:game :show-result?] true)
       (assoc-in [:game :result] game-result))))
