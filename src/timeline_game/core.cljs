(ns timeline-game.core
  (:require [re-frame.core :as rf]
            [reagent.dom :as reagent-dom]
            [timeline-game.init]
            [timeline-game.events]
            [timeline-game.view :as view]
            [timeline-game.db :as db]))

(rf/reg-event-fx
 ::load-app
 (fn [{:keys [db]} _]
   {:db (merge db (-> db/default-db))
    :dispatch [:new-game]}))

(defn ^:dev/after-load render []
  (rf/clear-subscription-cache!)
  (reagent-dom/render [view/root]
                      (js/document.getElementById "app")))

(defn ^:export init []
  (rf/dispatch-sync [::load-app])
  (render))
