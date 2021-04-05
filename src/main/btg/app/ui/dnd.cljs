(ns btg.app.ui.dnd
  (:require
   [re-frame.core :as rf]
   ["interactjs" :as interact]))

(rf/reg-event-fx
 :dnd/draggable
 (fn [{:keys [db]} [_ target options]]
   {:db db
    :init-draggable [target options]}))

(rf/reg-event-fx
 :dnd/enable-drag
 (fn [{:keys [db]} [_ target enabled?]]
   {:db db
    :enable-drag [target enabled?]}))

(rf/reg-event-fx
 :dnd/dropzone
 (fn [{:keys [db]} [_ target options]]
   {:db db
    :init-dropzone [target options]}))

(rf/reg-event-fx
 :dnd/enable-drop
 (fn [{:keys [db]} [_ target enabled?]]
   {:db db
    :enable-drop [target enabled?]}))

(rf/reg-fx
 :init-draggable
 (fn [[target options]]
   (let [options (update options :modifiers
                         (fn [m] [(.restrictRect (.-modifiers interact) (clj->js m))]))]
     (.draggable (interact target) (clj->js options)))))

(rf/reg-fx
 :enable-drag
 (fn [[target enabled?]]
   (.draggable (interact target) enabled?)))

(rf/reg-fx
 :init-dropzone
 (fn [[target options]]
   (.dropzone (interact target) (clj->js options))))

(rf/reg-fx
 :enable-drop
 (fn [[target enabled?]]
   (.dropzone (interact target) enabled?)))
