(ns timeline-game.view
  (:require [timeline-game.hand :as hand]
            [timeline-game.timeline :as timeline]
            [timeline-game.history :as history]))

(defn root []
  [:div
   [:h1 "Timeline Game"]

   [history/view]

   [:div.timeline
    [:h2 "Timeline"]
    [timeline/cards-in-timeline]]

   [:div.hand
    [:h2 "Hand"]
    [hand/cards-in-hand]]])
