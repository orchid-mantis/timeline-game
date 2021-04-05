(defproject timeline-game/server "0.1.0-SNAPSHOT"
  :description "Timeline game server"
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [com.taoensso/sente "1.16.2"]
                 [ring/ring "1.9.2"]
                 [ring/ring-defaults "0.3.2"]
                 [ring-cors/ring-cors "0.1.13"]
                 [http-kit/http-kit "2.5.3"]
                 [compojure/compojure "1.6.2"]]
  :source-paths ["src/main"]
  :main btg.server.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})