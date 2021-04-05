(ns btg.app.routes
  (:require-macros [secretary.core :refer [defroute]])
  (:import goog.History)
  (:require [secretary.core :as secretary]
            [goog.events :as gevents]
            [goog.history.EventType :as EventType]
            [re-frame.core :as rf]))

(rf/reg-event-db
 :set-active-panel
 (fn [db [_ active-panel]]
   (assoc db :active-panel active-panel)))

(rf/reg-sub
 :active-panel
 (fn [db _]
   (:active-panel db)))

(defn hook-browser-navigation! []
  (doto (History.)
    (gevents/listen
     EventType/NAVIGATE
     (fn [^EventType/NAVIGATE event]
       (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

(defn app-routes []
  (secretary/set-config! :prefix "#")
  ;; --------------------
  ;; define routes here
  (defroute "/" []
    (rf/dispatch [:set-active-panel :main-panel]))

  (defroute "/card-set" []
    (rf/dispatch [:set-active-panel :card-set-panel]))

  ;; --------------------
  (hook-browser-navigation!))
