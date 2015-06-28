(ns slackbot.core
  (:gen-class)
  (:require [slackbot.global :as global]
            [clojure.java.io :as io]
            [org.httpkit.server :refer :all]
            [cheshire.core :as json]
            [ring.middleware.reload :as reload]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [compojure.core :refer [defroutes GET POST]]))

(def resource-conf (-> "config.json" io/resource))

(defn read-conf [file]
  (json/parse-string (slurp (or file resource-conf)) true))

(defroutes routes
  (GET "/" [] "slackbot")
  (route/not-found "Not Found"))

(defn app-routes [{mode :mode}]
  (if (= mode "prod")
    (handler/site routes)
    (-> #'routes handler/site reload/wrap-reload)))

(defn -main [& [conf-file]]
  (let [conf (read-conf conf-file)
        app (app-routes conf)]
    (global/initialize-atoms conf)
    (run-server app {:port @global/server-port :join? false})))
