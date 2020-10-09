(ns timeline-game.core
  (:require [re-frame.core :as rf]
            [reagent.dom :as reagent-dom]
            ["mousetrap" :as mousetrap]
            [timeline-game.ui.dnd]
            [timeline-game.routes :as routes]
            [timeline-game.init]
            [timeline-game.events]
            [timeline-game.effects]
            [timeline-game.subs]
            [timeline-game.views :as views]
            [timeline-game.db :as db]))

(rf/reg-event-fx
 ::load-app
 (fn [{:keys [db]} _]
   {:db (merge db (-> db/default-db))
    :dispatch [:new-game]}))

(defn ^:dev/after-load render []
  (rf/clear-subscription-cache!)
  (reagent-dom/render [views/root]
                      (js/document.getElementById "app"))
  (js/window.addEventListener "resize" #(rf/dispatch [:timeline/update-scrollable])))

(defn init-key-bindings []
  (mousetrap/bind "left"  #(rf/dispatch [:user/scroll-timeline -100]))
  (mousetrap/bind "right" #(rf/dispatch [:user/scroll-timeline  100])))

(defn ^:export init []
  (routes/app-routes)
  (rf/dispatch-sync [::load-app])
  (init-key-bindings)
  (render))
