(ns timeline-game.ui.hand.events
  (:require
   [re-frame.core :as rf]))

(rf/reg-event-db
 :select-card
 (fn [db [_ id]]
   (update-in db [:player :selected-cards] conj id)))

(rf/reg-event-db
 :deselect-card
 (fn [db [_ id]]
   (update-in db [:player :selected-cards] disj id)))