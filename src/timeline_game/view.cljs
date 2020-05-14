(ns timeline-game.view
  (:require [re-frame.core :as rf]
            [timeline-game.hand :as hand]
            [timeline-game.timeline :as timeline]
            [timeline-game.history :as history]
            [timeline-game.game-result :as game-result]))

(defn root []
  [:div
   [:h1 "Timeline Game"]

   [:button
    {:on-click #(rf/dispatch [:new-game])
     :style {:width 150
             :padding "10px 20px"
             :display :block
             :margin "10px auto 0px auto"
             :cursor :pointer}}
    "Start a new game"]

   [game-result/view]
   [history/view]

   [:div.timeline
    [:h2 "Timeline"]
    [timeline/view]]

   [:div.hand
    [:h2 "Hand"]
    [hand/view]]])
