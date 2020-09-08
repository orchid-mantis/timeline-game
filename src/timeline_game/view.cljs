(ns timeline-game.view
  (:require [re-frame.core :as rf]
            [timeline-game.dropdown-panel :as dropdown]
            [timeline-game.hand :as hand]
            [timeline-game.ui.views :as ui]
            [timeline-game.ui.components :as uic]
            [timeline-game.history :as history]
            [timeline-game.card-set :as card-set]
            [timeline-game.players-stats :as players-stats]
            [timeline-game.game-result :as game-result]))

(defn main-panel []
  [:div.site
   [:header
    [:div.menu-wrapper
     [:div.menu
      [:span.title "Bible Timeline Game"]

      [:a.button
       {:on-click #(rf/dispatch [:new-game])}
       "Start a new game"]

      [:a.button
       {:on-click #(rf/dispatch [:overlay/toggle :history-overlay])}
       "Played cards"]]]]

   [:div.players
    [:div.players-stats
     [:div.column "Player"]
     [players-stats/view  :player]
     [:div.column "Bot"]
     [players-stats/view :bot]]]

   [:div.timeline
    [ui/timeline-view]]

   [:div.hand
    [hand/view]]

   [uic/overlay-view
    :history-overlay
    [history/overlay-view]]

   [game-result/view]])

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
