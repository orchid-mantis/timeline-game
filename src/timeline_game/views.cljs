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
      [ui/start-game-button]
      [ui/played-cards-button]]]]

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
   [ui/main-panel-button]
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
