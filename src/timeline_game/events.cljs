(ns timeline-game.events
  (:require [re-frame.core :as rf]
            [timeline-game.bot-player]
            [timeline-game.common :refer [remove-card ordered?]]
            [timeline-game.fsm :as fsm]))

(def turn-state-fsm
  {nil                  {:init-turn           :ready}
   :ready               {:correct-move        :well-placed-card
                         :wrong-move          :misplaced-card}
   :well-placed-card    {:correct-end-turn    :turn-ended}
   :misplaced-card      {:wrong-end-turn      :turn-ended}
   :turn-ended          {:init-turn           :ready}})

(def player-fsm
  {nil        {:next-player    :player}
   :player    {:next-player    :bot}
   :bot       {:next-player    :none}})

(def keep-state-in-ms 700)

;; -- Subscriptions -----------------------------------------------------------

(rf/reg-sub
 :deck
 (fn [db]
   (:deck db)))

(rf/reg-sub
 :cards
 (fn [db]
   (:cards db)))

(rf/reg-sub
 :turn
 (fn [db _]
   (get-in db [:game :turn])))

(rf/reg-sub
 :player
 (fn [db _]
   (get-in db [:game :player])))

;; -- Events ------------------------------------------------------------------

(defn update-next-state [db event]
  (fsm/update-next-state turn-state-fsm db [:game :turn] event))

(defn next-player [db event]
  (fsm/next-state player-fsm (get-in db [:game :player]) event))

(defn update-next-player [db event]
  (fsm/update-next-state player-fsm db [:game :player] event))

(rf/reg-event-fx
 :init-turn
 (fn [{:keys [db]} [event _]]
   (let [player (get-in db [:game :player])]
     (merge {:db (update-next-state db event)}
            (when (= player :bot) {:dispatch [:play-bot-move]})))))

(rf/reg-fx
 :timeout
 (fn [[time event]]
   (js/setTimeout #(rf/dispatch event) time)))

(defn historize [db player card-id valid-move?]
  (-> db
      (update-in [player :history :ids] conj card-id)
      (assoc-in [player :history :validity card-id] valid-move?)))

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
   {:db (update-next-state db event)
    :timeout [keep-state-in-ms [:correct-end-turn]]}))

(rf/reg-event-fx
 :correct-end-turn
 (fn [{:keys [db]} [event _]]
   {:db (update-next-state db event)
    :dispatch [:end-turn]}))

(rf/reg-event-fx
 :wrong-move
 (fn [{:keys [db]} [event player id]]
   {:db (update-next-state db event)
    :timeout [keep-state-in-ms [:wrong-end-turn player id]]}))

(defn draw-card [db player]
  (let [card-id (first (:deck db))]
    (if card-id
      (-> db
          (update-in [player :hand] conj card-id)
          (update :deck #(vec (drop 1 %))))
      db)))

(rf/reg-event-fx
 :wrong-end-turn
 (fn [{:keys [db]} [event player id]]
   (let [mode (get-in db [:game :mode])]
     {:db (cond-> db
            true (update-in [:timeline :ids] #(remove-card % id))
            true (update :deck #(conj % id))
            true (update-next-state event)
            (= mode :standard) (draw-card player))
      :dispatch [:end-turn]})))

(rf/reg-event-fx
 :end-turn
 (fn [{:keys [db]} _]
   (let [next-player (next-player db :next-player)]
     {:db db
      :dispatch (if (= next-player :none)
                  [:eval-round]
                  [:next-player])})))

(rf/reg-event-fx
 :next-player
 (fn [{:keys [db]} [event _]]
   {:db (update-next-player db event)
    :dispatch [:init-turn]}))

(defn eval-standard [db]
  (let [player-hand (get-in db [:player :hand])
        bot-hand (get-in db [:bot :hand])]
    (cond
      (and (empty? player-hand) (empty? bot-hand)) [true :sudden-death]
      (empty? player-hand) [false :player-won]
      (empty? bot-hand)    [false :player-lost]
      :else                [true :standard])))

(defn well-played? [history]
  (let [last-played-card (first (:ids history))]
    (get (:validity history) last-played-card)))

(defn eval-sudden-death [db]
  (let [player-history (get-in db [:player :history])
        bot-history (get-in db [:bot :history])
        player-well-played? (well-played? player-history)
        bot-well-played? (well-played? bot-history)]
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

(defn all-players-draw-card [db]
  (reduce #(draw-card %1 %2) db [:player :bot]))

(defn handle-mode-change [db mode]
  (cond (= mode :sudden-death)
        (-> db
            (assoc-in [:game :mode] mode)
            (all-players-draw-card))
        :else db))

(rf/reg-event-fx
 :next-round
 (fn [{:keys [db]} [_ mode]]
   {:db (-> db
            (assoc-in [:game :player] nil)
            (handle-mode-change mode))
    :dispatch [:next-player]}))

(rf/reg-event-db
 :game-end
 (fn [db [_ game-result]]
   (assoc-in db [:game :result] game-result)))
