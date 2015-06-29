(ns slackbot.core
  (:gen-class)
  (:require [slackbot.global :as global]
            [clojure.java.io :as io]
            [clojure.string :as string]
            [org.httpkit.server :refer :all]
            [cheshire.core :as json]
            [clj-http.client :as client]
            [ring.middleware.reload :as reload]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [compojure.core :refer [defroutes GET POST]]))

(def resource-conf (-> "config.json" io/resource))

(defn read-conf [file]
  (json/parse-string (slurp (or file resource-conf)) true))

(defn post-to-slack [message]
  (client/post @global/api-url
               {:body (json/generate-string {:channel @global/channel
                                             :username "slackard"
                                             :text message})}))

(defmulti do-command (fn [command _ _] command))

(defmethod do-command :default [command user _]
  (post-to-slack (str "Sorry, " user ", I don't seem to know about " command ".")))

(defn exec-user-command [{:keys [user text]}]
  (let [[_ command & search-terms] (string/split text #"\s")]
    (do-command command user search-terms)))

(defn process-incoming-webhook [username text]
  (exec-user-command {:user username :text text}))

(defroutes routes
  (GET "/" [] "slackard here")
  (POST "/sparc/" [user_name text] (process-incoming-webhook user_name text))
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
