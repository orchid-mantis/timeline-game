(defproject timeline-game/server "0.1.0-SNAPSHOT"
  :description "Timeline game server"
  :dependencies [[org.clojure/clojure "1.10.3"]]
  :source-paths ["src/main"]
  :main server.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})