(ns timeline-game.ui.views
  (:require
   [timeline-game.ui.timeline.views :as timeline]
   [timeline-game.ui.hand.views :as hand]
   [timeline-game.ui.card-set.views :as card-set]))

;;; Views ;;;

(def timeline-view timeline/view)
(def hand-view hand/view)

(def card-set-view card-set/view)