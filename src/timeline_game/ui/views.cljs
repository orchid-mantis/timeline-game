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

(def timeline timeline/view)
(def hand hand/view)
(def status-bar status-bar/view)
(def players-stats players-stats/view)
(def card-history card-history/view)
(def game-result game-result/view)

(def card-set card-set/view)
