(ns timeline-game.ui.components
  (:require
   [timeline-game.ui.components.ribbons :as ribbons]
   [timeline-game.ui.components.views :as views]
   [timeline-game.ui.components.buttons :as buttons]
   [timeline-game.ui.components.dropdown-panel :as dropdown-panel]
   [timeline-game.ui.components.overlay :as overlay]))

;;; Components ;;;
(def timeline-ribbon ribbons/timeline-ribbon)

(def start-game-button buttons/start-game-button)
(def played-cards-button buttons/played-cards-button)
(def main-panel-button buttons/main-panel-button)

(def dropdown-panel-view dropdown-panel/view)
(def overlay-view overlay/view)

(def hourglass views/hourglass)