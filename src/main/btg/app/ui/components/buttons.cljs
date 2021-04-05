(ns btg.app.ui.components.buttons
  (:require
   [re-frame.core :as rf]
   [btg.app.ui.components.views :as ui]))

(defn start-game-button []
  (let [allow-action? (rf/subscribe [:allow-action?])]
    [ui/button {:on-click #(rf/dispatch [:new-game])
                :label "Start a new game"
                :enabled @allow-action?}]))

(defn played-cards-button []
  [ui/button {:on-click #(rf/dispatch [:overlay/toggle :history-overlay])
              :label "Played cards"}])

(defn main-panel-button []
  [ui/button {:href "#/"
              :label "Return to game"}])
