(ns timeline-game.init
  (:require [re-frame.core :as rf]))

(def hand-size 5)

(def players {:player {:name "Player"} :bot {:name "Bot"}})

(defn init-deck [db]
  (let [cards (keys (:cards db))
        deck (shuffle cards)]
    (assoc db :deck (vec deck))))

(defn deal-cards [hand-size deck]
  [(take hand-size deck) (vec (drop hand-size deck))])

(defn init-hand [db player]
  (let [[hand deck] (deal-cards hand-size (:deck db))]
    (-> db
        (assoc-in [player :hand :ids] hand)
        (assoc-in [player :hand :state] :ready)
        (assoc :deck deck))))

(defn init-player [db player]
  (-> db
      (assoc-in [player :selected-card-id] :nothing)
      (init-hand player)
      (assoc-in [player :stats] {:well-played-count 0
                                 :wrong-played-count 0
                                 :last-played-card-id nil})))

(defn init-players [db players]
  (assoc
   (reduce #(init-player %1 %2) db (keys players))
   :players players))

(defn init-timeline [db]
  (let [cards-ids (take 1 (:deck db))]
    (-> db
        (assoc-in [:timeline :ids] (vec cards-ids))
        (update :deck #(drop 1 %)))))

(defn init-game-state [db]
  (assoc db :game {:mode :standard :result :await :round 0}))

(defn init-game [db]
  (-> db
      init-game-state
      init-deck
      init-timeline
      (assoc :history {})
      (init-players players)))

(rf/reg-event-fx
 :new-game
 (fn [{:keys [db]} _]
   {:db (init-game db)
    :dispatch [:next-round :standard]}))
