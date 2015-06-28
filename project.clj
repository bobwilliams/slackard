(defproject slackbot "0.1.0-SNAPSHOT"
  :description "A simple bot for slack"
  :url "https://github.com/bobwilliams/slackbot"
  :license {:name "MIT"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [ [org.clojure/clojure "1.6.0"]
                  [cheshire "5.4.0"]
                  [compojure "1.3.4"]
                  [ring "1.4.0-RC1"]
                  [http-kit "2.1.18"]]
  :main slackbot.core)1