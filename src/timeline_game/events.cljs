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

(def debug (rf/after (fn [db event]
                       (.log js/console "=======")
                       (.log js/console "player: " (str (get-in db [:game :player])))
                       (.log js/console "state: " (str (get-in db [:game :turn])))
                       (.log js/console "event: " (str event)))))

(def interceptors [debug])

(defn update-next-state [db event]
  (fsm/update-next-state turn-state-fsm db [:game :turn] event))

(defn next-player [db event]
  (fsm/next-state player-fsm (get-in db [:game :player]) event))

(defn update-next-player [db event]
  (fsm/update-next-state player-fsm db [:game :player] event))

(rf/reg-event-fx
 :init-turn
 interceptors
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
   (let [timeline (get-in db [:timeline :ids])
         valid-move? (ordered? timeline)]
     {:db (-> db
              (assoc-in [:timeline :last-added-id] id)
              (historize player id valid-move?))
      :dispatch (if valid-move?
                  [:correct-move]
                  [:wrong-move player id])})))

(rf/reg-event-fx
 :correct-move
 interceptors
 (fn [{:keys [db]} [event _]]
   {:db (update-next-state db event)
    :timeout [300 [:correct-end-turn]]}))

(rf/reg-event-fx
 :correct-end-turn
 interceptors
 (fn [{:keys [db]} [event _]]
   {:db (update-next-state db event)
    :dispatch [:end-turn]}))

(rf/reg-event-fx
 :wrong-move
 interceptors
 (fn [{:keys [db]} [event player id]]
   {:db (update-next-state db event)
    :timeout [300 [:wrong-end-turn player id]]}))

(defn draw-card [db player]
  (let [card-id (first (:deck db))]
    (if card-id
      (-> db
          (update-in [player :hand] conj card-id)
          (update :deck #(drop 1 %)))
      db)))

(rf/reg-event-fx
 :wrong-end-turn
 interceptors
 (fn [{:keys [db]} [event player id]]
   {:db (-> db
            (update-in [:timeline :ids] #(remove-card % id))
            (draw-card player)
            (update-next-state event))
    :dispatch [:end-turn]}))

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

(defn evaluate-round [[player-hand bot-hand]]
  (cond
    (and (empty? player-hand) (empty? bot-hand)) [false :tie]
    (empty? player-hand) [false :player-won]
    (empty? bot-hand) [false :player-lost]
    :else [true nil]))

(rf/reg-event-fx
 :eval-round
 (fn [{:keys [db]} _]
   (let [players-hands [(get-in db [:player :hand]) (get-in db [:bot :hand])]
         [next-round? game-result] (evaluate-round players-hands)]
     {:db db
      :dispatch (if next-round?
                  [:next-round]
                  [:game-end game-result])})))

(rf/reg-event-fx
 :next-round
 (fn [{:keys [db]} _]
   {:db (assoc-in db [:game :player] nil)
    :dispatch [:next-player]}))

(rf/reg-event-db
 :game-end
 (fn [db [_ game-result]]
   (assoc-in db [:game :result] game-result)))
