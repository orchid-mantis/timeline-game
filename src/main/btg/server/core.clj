(ns btg.server.core
  (:require [btg.server.router :as router]
            [btg.server.http-server :as server]
            [btg.server.socket :as socket]))

(defn start! []
  (router/start!)
  (server/start! 5000)
  (socket/start-example-broadcaster!))

(defn stop! []
  (router/stop!)
  (server/stop!))

(defn -main []
  (start!))
