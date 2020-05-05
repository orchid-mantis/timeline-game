(ns timeline-game.view
  (:require [timeline-game.hand :as hand]
            [timeline-game.timeline :as timeline]))

(defn root []
  [:div
   [:h1 "Timeline Game"]

   [:div.timeline
    [:h2 "Timeline"]
    [timeline/cards-in-timeline]]

   [:div.hand
    [:h2 "Hand"]
    [hand/cards-in-hand]]])
