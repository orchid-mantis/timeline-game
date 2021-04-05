(ns btg.app.views
  (:require [re-frame.core :as rf]
            [btg.app.ui.views :as view]
            [btg.app.ui.components :as ui]
            [btg.app.ui.components.svg-images :as svg]
            [dv.cljs-emotion-reagent :refer [theme-provider]]))

(defn main-panel []
  (let [theme (rf/subscribe [:app/theme])]
    (theme-provider
     @theme
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
      [svg/card-front]])))

(defn card-set-panel []
  [:div
   [ui/main-panel-button]
   [view/card-set]

   [svg/card-border-clip]
   [svg/card-front]])

(defn connect-button []
  [:input {:type     :button
           :value    "Connect"
           :disabled @(rf/subscribe [:comm/connected])
           :on-click #(rf/dispatch [:comm/connect])}])

(defn comm-test-panel []
  (let [push-count (or @(rf/subscribe [:comm/push-count]) 0)]
    [:div
     [:p (str "Push count: " push-count)]
     [connect-button]
     [:p "(push data is logged to the console)"]]))

(defn- panels [panel-name]
  (case panel-name
    :main-panel [main-panel]
    :card-set-panel [card-set-panel]
    :comm-test-panel [comm-test-panel]
    [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn root []
  (let [active-panel (rf/subscribe [:active-panel])]
    [:div
     [show-panel @active-panel]]))
