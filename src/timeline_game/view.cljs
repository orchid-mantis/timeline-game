(ns timeline-game.view
  (:require [re-frame.core :as rf]
            [timeline-game.dropdown-panel :as dropdown]
            [timeline-game.card_history_overview :as hist-overview]
            [timeline-game.hand :as hand]
            [timeline-game.timeline :as timeline]
            [timeline-game.history :as history]
            [timeline-game.card-set :as card-set]
            [timeline-game.game-result :as game-result]))

(defn main-panel []
  [:div
   [:h1 "Timeline Game"]

  ;;  [:a.button {:href "#/card-set"} "View all cards"]

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

(defn card-set-panel []
  [:div
   [:a.button {:href "#/"} "Return to game"]
   [card-set/view]])

(defn- panels [panel-name]
  (case panel-name
    :main-panel [main-panel]
    :card-set-panel [card-set-panel]
    [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn root []
  (let [active-panel (rf/subscribe [:active-panel])]
    [:div
     [show-panel @active-panel]]))
