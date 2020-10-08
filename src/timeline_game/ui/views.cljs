(ns timeline-game.ui.views
  (:require
   [timeline-game.ui.timeline.views :as timeline]
   [timeline-game.ui.hand.views :as hand]
   [timeline-game.ui.status-bar.views :as status-bar]
   [timeline-game.ui.players-stats.views :as players-stats]
   [timeline-game.ui.card-history.views :as card-history]
   [timeline-game.ui.game-result.views :as game-result]
   [timeline-game.ui.card-set.views :as card-set]))

;;; Views ;;;

(def timeline-view timeline/view)
(def hand-view hand/view)
(def status-bar-view status-bar/view)
(def players-stats-view players-stats/view)
(def card-history-view card-history/view)
(def game-result-view game-result/view)

(def card-set-view card-set/view)