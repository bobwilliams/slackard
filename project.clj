(defproject slackard "0.1.0-SNAPSHOT"
  :description "A simple bot for slack"
  :url "https://github.com/bobwilliams/slackard"
  :license {:name "MIT"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [ [org.clojure/clojure "1.6.0"]
                  [cheshire "5.4.0"]
                  [compojure "1.3.4"]
                  [ring "1.4.0-RC1"]
                  [clj-http "1.1.0"]
                  [http-kit "2.1.18"]
                  [org.julienxx/clj-slack "0.4.3"]
                  [environ "1.0.0"]]
  :main slackard.core)