(ns timeline-game.themes
  (:require [re-frame.core :as rf]))

(def default-theme
  {:theme
   {:ribbon
    {:color "crimson"}}})

(def image-card-theme
  {:theme
   {:ribbon
    {:color "grey"}}})

(rf/reg-event-db
 :app/set-theme
 (fn [db [_ theme]]
   (assoc-in db [:app :theme] theme)))

(rf/reg-sub
 :app/theme
 (fn [db _]
   (get-in db [:app :theme])))

; (rf/dispatch [:app/set-theme image-card-theme])