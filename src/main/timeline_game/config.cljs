(ns timeline-game.config
  (:require
   [timeline-game.env :as env]))

(defn game-card []
  (env/get :game-card))

(defn hand-size []
  (env/get :hand-size))

(defn delay-enabled? []
  (get (env/get :auto-side-scroll) :delay-enabled?))

(defn min-delay []
  (get (env/get :auto-side-scroll) :min-delay))

(defn max-delay []
  (get (env/get :auto-side-scroll) :max-delay))
