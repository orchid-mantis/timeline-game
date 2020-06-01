(ns timeline-game.view
  (:require [re-frame.core :as rf]
            [timeline-game.dropdown-panel :as dropdown]
            [timeline-game.card_history_overview :as hist-overview]
            [timeline-game.hand :as hand]
            [timeline-game.timeline :as timeline]
            [timeline-game.history :as history]
            [timeline-game.game-result :as game-result]))

(defn root []
  [:div
   [:h1 "Timeline Game"]

   [:button.btn
    {:on-click #(rf/dispatch [:new-game])}
    "Start a new game"]

   [game-result/view]

   [dropdown/view
    "Player's history"
    [hist-overview/view (rf/subscribe [:history/overview :player])]
    [history/view (rf/subscribe [:history/played-cards :player])]]

   [dropdown/view
    "Bot's history"
    [hist-overview/view (rf/subscribe [:history/overview :bot])]
    [history/view (rf/subscribe [:history/played-cards :bot])]]

   [:div {:style {:clear :both}}]

   [:div.timeline
    [:h2 "Timeline"]
    [timeline/view]]

   [:div.hand
    [:h2 "Hand"]
    [hand/view]]])
