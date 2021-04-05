(ns btg.app.ui.utils
  (:require
   [clojure.string :as str]))

(defn cs [& args]
  (str/join " " (map name (filter identity args))))
