(ns timeline-game.ui.components.buttons
  (:require
   [re-frame.core :as rf]
   [timeline-game.ui.components.views :as ui]))

(defn start-game-button []
  (let [allow-new-game? (rf/subscribe [:allow-new-game?])]
    [ui/button {:on-click #(rf/dispatch [:new-game])
                :label "Start a new game"
                :enabled @allow-new-game?}]))

(defn played-cards-button []
  [ui/button {:on-click #(rf/dispatch [:overlay/toggle :history-overlay])
              :label "Played cards"}])

(defn main-panel-button []
  [ui/button {:href "#/"
              :label "Return to game"}])
