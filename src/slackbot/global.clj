(ns slackbot.global)

(def api-url (atom "https://slack.com/api"))
(def api-token (atom "your token"))
(def server-port (atom 3000))
(def channel (atom "#general"))

(defn update-atom [atom value]
  (if value (reset! atom value)))

(defn initialize-atoms [conf]
  (update-atom api-url (or (:api-url conf) "https://slack.com/api"))
  (update-atom api-token (or (:api-token conf) "your token"))
  (update-atom server-port (or (:server-port conf) 3000))
  (update-atom channel (or (:channel conf) "#general")))