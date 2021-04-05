(ns btg.app.config
  (:require
   [btg.app.env :as env]))

(defn game-card []
  (env/get :game-card))

(defn hand-size []
  (env/get :hand-size))

(defn auto-scroll-delay-enabled? []
  (get (env/get :auto-scroll) :delay-enabled?))

(defn auto-scroll-min-delay []
  (get (env/get :auto-scroll) :min-delay))

(defn auto-scroll-max-delay []
  (get (env/get :auto-scroll) :max-delay))
