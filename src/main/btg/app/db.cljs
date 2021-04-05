(ns btg.app.db
  (:require
   [btg.app.cards :as cards]))

(def default-db
  {:cards (cards/indexed-cards)})
