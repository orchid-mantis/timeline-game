(ns timeline-game.ui.components
  (:require
   [timeline-game.ui.components.dropdown-panel :as dropdown-panel]
   [timeline-game.ui.components.overlay :as overlay]
   [timeline-game.ui.components.basic-card :as basic-card]))

;;; Components ;;;
(def dropdown-panel-view dropdown-panel/view)
(def overlay-view overlay/view)
(def basic-card-view basic-card/view)