(ns timeline-game.ui-helpers
  (:require [clojure.string :as str]))

(defn cs [& args]
  (str/join " " (map name (filter identity args))))
