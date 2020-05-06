(ns timeline-game.core
  (:require [re-frame.core :as rf]
            [reagent.dom :as reagent-dom]
            [timeline-game.events]
            [timeline-game.view :as view]
            [timeline-game.db :as db]))

(def hand-size 5)

(defn init-deck [db]
  (let [cards (keys (:cards db))
        deck (shuffle cards)]
    (assoc db :deck deck)))

(defn init-hand [db]
  (let [cards-ids (take hand-size (:deck db))]
    (-> db
        (assoc-in [:player :hand] cards-ids)
        (update :deck #(drop hand-size %)))))

(defn init-player [db]
  (-> db
      (assoc :player {:active? true})
      init-hand))

(defn init-timeline [db]
  (let [cards-ids (take 1 (:deck db))]
    (-> db
        (assoc-in [:timeline :ids] (vec cards-ids))
        (update :deck #(drop 1 %)))))

(rf/reg-event-fx
 ::load-app
 (fn [{:keys [db]} _]
   {:db (merge db (-> db/default-db
                      init-deck
                      init-timeline
                      init-player))}))

(rf/reg-sub
 :deck
 (fn [db]
   (:deck db)))

(rf/reg-sub
 :cards
 (fn [db]
   (:cards db)))

(rf/reg-sub
 :player-turn?
 (fn [db]
   (get-in db [:player :active?])))

(defn ^:dev/after-load render []
  (rf/clear-subscription-cache!)
  (reagent-dom/render [view/root]
                      (js/document.getElementById "app")))

(defn ^:export init []
  (rf/dispatch-sync [::load-app])
  (render))
