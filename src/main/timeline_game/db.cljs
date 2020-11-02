(ns timeline-game.db
  (:require
   [timeline-game.cards :as cards]))

(def default-db
  {:cards (cards/indexed-cards)})
