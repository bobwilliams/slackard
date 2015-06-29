(ns slackard.core
  (:gen-class)
  (:require 
    [slackard.commands.youtube :as yt]
    [org.httpkit.server :refer :all]
    [ring.middleware.reload :as reload]
    [compojure.route :as route]
    [compojure.handler :as handler]
    [compojure.core :refer [defroutes GET POST]]
    [clj-slack.chat :as chat]
    [environ.core :as e]))

(def connection {:api-url (e/env :api-url)  :token (e/env :api-token)})

(defroutes routes
  (GET "/" [] "slackard here")
  (POST "/" [query] (clj-slack.chat/post-message connection (:channel_name (str "#" query)) (:text query)))
  (route/not-found "Not Found"))

(defn app-routes [mode]
  (if (= mode "prod")
    (handler/site routes)
    (-> #'routes handler/site reload/wrap-reload)))

(defn -main []
  (let [app (app-routes (e/env :mode))]
    (run-server app {:port (read-string (e/env :server-port)) :join? false})))