(ns btg.app.ui.views
  (:require
   [btg.app.ui.timeline.views :as timeline]
   [btg.app.ui.hand.views :as hand]
   [btg.app.ui.status-bar.views :as status-bar]
   [btg.app.ui.players-stats.views :as players-stats]
   [btg.app.ui.card-history.views :as card-history]
   [btg.app.ui.game-result.views :as game-result]
   [btg.app.ui.card-set.views :as card-set]))

;;; Views ;;;

(def timeline timeline/view)
(def hand hand/view)
(def status-bar status-bar/view)
(def players-stats players-stats/view)
(def card-history card-history/view)
(def game-result game-result/view)

(def card-set card-set/view)
