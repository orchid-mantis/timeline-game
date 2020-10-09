(ns timeline-game.views
  (:require [re-frame.core :as rf]
            [timeline-game.ui.views :as view]
            [timeline-game.ui.components :as ui]))

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

   [view/status-bar]

   [:div.players
    [view/players-stats]]

   [:div.table
    [view/timeline]
    [view/hand]]

   [ui/overlay-view
    :history-overlay
    [view/card-history]]

   [view/game-result]])

(defn card-set-panel []
  [:div
   [:a.button {:href "#/"} "Return to game"]
   [view/card-set]])

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
