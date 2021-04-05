(ns btg.app.ui.components
  (:require
   [btg.app.ui.components.ribbons :as ribbons]
   [btg.app.ui.components.views :as views]
   [btg.app.ui.components.buttons :as buttons]
   [btg.app.ui.components.dropdown-panel :as dropdown-panel]
   [btg.app.ui.components.overlay :as overlay]))

;;; Components ;;;
(def timeline-ribbon ribbons/timeline-ribbon)

(def start-game-button buttons/start-game-button)
(def played-cards-button buttons/played-cards-button)
(def main-panel-button buttons/main-panel-button)

(def dropdown-panel-view dropdown-panel/view)
(def overlay-view overlay/view)

(def hourglass views/hourglass)