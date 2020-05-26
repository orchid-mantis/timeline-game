(ns timeline-game.init
  (:require [re-frame.core :as rf]
            [kixi.stats.distribution :refer [sample bernoulli]]))

(def hand-size 5)

(def players [:player :bot])

(defn init-deck [db]
  (let [cards (keys (:cards db))
        deck (shuffle cards)]
    (assoc db :deck deck)))

(defn deal-cards [hand-size deck]
  [(take hand-size deck) (drop hand-size deck)])

(defn init-hand [db player]
  (let [[hand deck] (deal-cards hand-size (:deck db))]
    (-> db
        (assoc-in [player :hand] hand)
        (assoc :deck deck))))

(defn init-player [db player]
  (-> db
      (assoc-in [player :history] {:ids '() :validity {}})
      (init-hand player)))

(defn init-players [db players]
  (reduce #(init-player %1 %2) db players))

(defn init-timeline [db]
  (let [cards-ids (take 1 (:deck db))]
    (-> db
        (assoc-in [:timeline :ids] (vec cards-ids))
        (update :deck #(drop 1 %)))))

(defn init-game-state [db]
  (assoc db :game {:result :await}))

(defn init-success-rate-distribution [db]
  (assoc-in db [:bot :success-dist] (sample (* hand-size 3) (bernoulli {:p 0.9}))))

(defn init-game [db]
  (-> db
      init-game-state
      init-deck
      init-timeline
      (init-players players)
      init-success-rate-distribution))

(defn handle-new-game
  [{:keys [db]} _]
  {:db (init-game db)
   :dispatch [:next-round]})

(rf/reg-event-fx :new-game handle-new-game)
