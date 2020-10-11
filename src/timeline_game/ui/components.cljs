(ns timeline-game.ui.components
  (:require
   [timeline-game.ui.components.views :as views]
   [timeline-game.ui.components.buttons :as buttons]
   [timeline-game.ui.components.dropdown-panel :as dropdown-panel]
   [timeline-game.ui.components.overlay :as overlay]
   [timeline-game.ui.components.basic-card :as basic-card]))

;;; Components ;;;

(def start-game-button buttons/start-game-button)
(def played-cards-button buttons/played-cards-button)
(def main-panel-button buttons/main-panel-button)

(def dropdown-panel-view dropdown-panel/view)
(def overlay-view overlay/view)
(def basic-card-view basic-card/view)

(def hourglass views/hourglass)