(ns timeline-game.bot-player
  (:require [re-frame.core :as rf]
            [clojure.set :as set]
            [timeline-game.common :refer [put-before remove-card]]))

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
 :play-bot-move
 (fn [{:keys [db]} _]
   (let [[id new-hand] (select-card (get-in db [:bot :hand]))
         distribution (get-in db [:bot :success-dist])]
     {:db (-> db
              (assoc-in [:bot :hand] new-hand)
              (update-in [:timeline :ids] bot-place-card id (peek distribution))
              (assoc-in [:bot :success-dist] (pop distribution)))
      :dispatch [:eval-move :bot id]})))
