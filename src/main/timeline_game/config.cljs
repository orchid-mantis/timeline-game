(ns timeline-game.config
  (:require
   [timeline-game.env :as env]))

(defn game-card []
  (env/get :game-card))

(defn hand-size []
  (env/get :hand-size))
