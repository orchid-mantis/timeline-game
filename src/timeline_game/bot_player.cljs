(ns timeline-game.bot-player
  (:require [re-frame.core :as rf]
            [clojure.set :as set]
            [timeline-game.common :refer [put-before remove-card]]
            [kixi.stats.distribution :refer [draw bernoulli]]))

(defn select-card [hand]
  (let [card-id (first (shuffle hand))]
    [card-id (remove-card hand card-id)]))

(defn find-index [needle haystack]
  (first (keep-indexed #(when (= %2 needle) %1) haystack)))

(defn place-card-correct [timeline card-id]
  (let [[lesser greater] (split-with #(> card-id %) timeline)
        new-timeline (vec (concat lesser [card-id] greater))]
    [(find-index card-id new-timeline) new-timeline]))

(defn place-card-wrong [timeline card-id]
  (let [[correct-pos correct-timeline] (place-card-correct timeline card-id)
        all-pos (range (count correct-timeline))
        all-wrong-pos (into [] (set/difference (set all-pos) #{correct-pos}))
        random-pos (rand-nth all-wrong-pos)]
    [random-pos (put-before timeline random-pos card-id)]))

(defn bot-place-card [timeline card-id bot-success?]
  (cond
    (nil? card-id) timeline
    bot-success? (place-card-correct timeline card-id)
    :else (place-card-wrong timeline card-id)))

(rf/reg-cofx
 :bot-success?
 (fn [cofx]
   (assoc cofx :bot-success? (draw (bernoulli {:p 0.9})))))

(rf/reg-event-fx
 :play-bot-move
 [(rf/inject-cofx :bot-success?)]
 (fn [{:keys [db bot-success?]} _]
   (let [[id new-hand] (select-card (get-in db [:bot :hand :ids]))
         timeline (get-in db [:timeline :ids])
         [pos new-timeline] (bot-place-card timeline id bot-success?)
         timeline-node (get-in db [:dom-nodes :timeline])
         card-width (get-in db [:app :game-card :width])]
     {:db (assoc-in db [:bot :last-move] {:id id
                                          :hand new-hand
                                          :timeline new-timeline})
      :side-scroll [timeline-node card-width pos #(rf/dispatch [:side-scroll-finished])]})))

(rf/reg-event-fx
 :side-scroll-finished
 (fn [{:keys [db]} _]
   (let [last-move (get-in db [:bot :last-move])]
     {:db (-> db
              (assoc-in [:bot :hand :ids] (:hand last-move))
              (assoc-in [:timeline :ids] (:timeline last-move)))
      :dispatch [:eval-move :bot (:id last-move)]})))
