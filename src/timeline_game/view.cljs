(ns timeline-game.view
  (:require [timeline-game.hand :as hand]
            [timeline-game.timeline :as timeline]
            [timeline-game.history :as history]
            [timeline-game.game-result :as game-result]))

(defn root []
  [:div
   [:h1 "Timeline Game"]

   [game-result/view]
   [history/view]

   [:div.timeline
    [:h2 "Timeline"]
    [timeline/view]]

   [:div.hand
    [:h2 "Hand"]
    [hand/view]]])
