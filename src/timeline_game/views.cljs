(ns timeline-game.views
  (:require [re-frame.core :as rf]
            [timeline-game.ui.views :as view]
            [timeline-game.ui.components :as ui]
            [timeline-game.ui.components.svg-images :as svg]))

(defn main-panel []
  [:div
   [:header
    [:div.menu-wrapper
     [:div.menu
      [:span.title "Bible Timeline Game"]
      [ui/start-game-button]
      [ui/played-cards-button]]]]

   [view/status-bar]

   [:div.players
    [view/players-stats]]

   [ui/hourglass]

   [:div.table
    [view/timeline]
    [view/hand]]

   [ui/overlay-view
    :history-overlay
    [view/card-history]]

   [view/game-result]

   [svg/card-border-clip]
   [svg/card-back]
   [svg/card-front]])

(defn card-set-panel []
  [:div
   [ui/main-panel-button]
   [view/card-set]

   [svg/card-border-clip]
   [svg/card-front]])

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
