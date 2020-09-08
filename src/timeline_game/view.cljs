(ns timeline-game.view
  (:require [re-frame.core :as rf]
            [timeline-game.ui.views :as ui]
            [timeline-game.ui.components :as uic]
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
     [ui/players-stats-view :player]
     [:div.column "Bot"]
     [ui/players-stats-view :bot]]]

   [:div.timeline
    [ui/timeline-view]]

   [:div.hand
    [ui/hand-view]]

   [uic/overlay-view
    :history-overlay
    [ui/card-history-view]]

   [game-result/view]])

(defn card-set-panel []
  [:div
   [:a.button {:href "#/"} "Return to game"]
   [ui/card-set-view]])

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
