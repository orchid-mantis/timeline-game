(ns timeline-game.env
  (:refer-clojure :exclude [get])
  (:require [shadow-env.core :as env]
            #?(:clj [aero.core :as aero])
            #?(:clj [clojure.java.io :as io])))

#?(:clj
   (defn read-env [build-state]
     (let [profile (keyword (System/getenv "ENV"))
           aero-config {:profile (or profile :dev)}]
       {:cljs (-> (io/resource "config.edn")
                  (aero/read-config aero-config))})))

(env/link get `read-env)
