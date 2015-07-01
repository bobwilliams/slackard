(ns slackard.core
  (:gen-class)
  (:require 
    [org.httpkit.server :refer :all]
    [ring.middleware.reload :as reload]
    [compojure.route :as route]
    [compojure.handler :as handler]
    [compojure.core :refer [defroutes GET POST]]
    [cheshire.core :as json]
    [clj-slack.chat :as chat]
    [environ.core :as e]))

(def index-html "<h1><a href='https://github.com/bobwilliams/slackard'>Slackard<a/></h1>A Bot for Slack written in Clojure")
(def connection {:api-url (e/env :api-url)  :token (e/env :api-token)})

(defroutes routes
  (GET "/" [] index-html)
  (POST "/test" req (str (:params req)))
  (route/not-found "Not Found"))

(defn app-routes [mode]
  (if (= mode "prod")
    (handler/site routes)
    (-> #'routes handler/site reload/wrap-reload)))

(defn -main []
  (let [app (app-routes (e/env :mode))]
    (run-server app {:port (read-string (e/env :server-port)) :join? false})))