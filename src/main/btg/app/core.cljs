(ns btg.app.core
  (:require [re-frame.core :as rf]
            [reagent.dom :as reagent-dom]
            ["mousetrap" :as mousetrap]
            [btg.app.ui.dnd]
            [btg.app.ui.card-provider]
            [btg.app.routes :as routes]
            [btg.app.comm.client]
            [btg.app.comm.events]
            [btg.app.comm.subs]
            [btg.app.init]
            [btg.app.events]
            [btg.app.effects]
            [btg.app.subs]
            [btg.app.views :as views]
            [btg.app.db :as db]
            [btg.app.config :as conf]))

(rf/reg-event-fx
 ::load-app
 (fn [{:keys [db]} _]
   {:db (merge db (-> db/default-db))
    :dispatch-n [[:game-card/init (conf/game-card)]
                 [:new-game]
                 [:comm/init]
                 ]}))

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
