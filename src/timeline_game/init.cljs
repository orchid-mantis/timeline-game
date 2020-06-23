(ns timeline-game.init
  (:require [re-frame.core :as rf]
            [kixi.stats.distribution :refer [sample bernoulli]]))

(def hand-size 5)

(def players [:player :bot])

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
        (assoc :deck deck))))

(defn init-player [db player]
  (-> db
      (assoc-in [player :history] {:ids '() :validity {}})
      (assoc-in [player :selected-card-id] :nothing)
      (init-hand player)))

(defn init-players [db players]
  (reduce #(init-player %1 %2) db players))

(defn init-timeline [db]
  (let [cards-ids (take 1 (:deck db))]
    (-> db
        (assoc-in [:timeline :ids] (vec cards-ids))
        (update :deck #(drop 1 %)))))

(defn init-game-state [db]
  (assoc db :game {:mode :standard :result :await}))

(defn init-success-rate-distribution [db]
  (let [card-count (count (:cards db))]
    (assoc-in db [:bot :success-dist] (sample card-count (bernoulli {:p 0.9})))))

(defn init-game [db]
  (-> db
      init-game-state
      init-deck
      init-timeline
      (init-players players)
      init-success-rate-distribution))

(rf/reg-event-fx
 :new-game
 (fn [{:keys [db]} _]
   {:db (init-game db)
    :dispatch [:next-round]}))
