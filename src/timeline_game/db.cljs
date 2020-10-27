(ns timeline-game.db
  (:require
   [timeline-game.cards :as cards]
   [timeline-game.themes :as themes]))

(def default-db
  {:cards (cards/indexed-cards)
   :app
   {:theme themes/default-theme}})
