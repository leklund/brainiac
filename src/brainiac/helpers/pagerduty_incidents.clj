(ns brainiac.helpers.pagerduty-incidents
  (:use [clojure.contrib.json :only (read-json)]
        [clojure.java.io :only (reader)])
  (:require [brainiac.plugin :as brainiac]))

(defn url [organization]
  (let [status "triggered,acknowledged"
        base-url (format "https://%s.pagerduty.com/api/v1/incidents" organization)]
    (format "%s?status=%s&sort_by=created_on:desc" base-url status)))

(defn transform [stream]
  (let [json (read-json (reader stream))
        incidents (:incidents json)]
    {:incidents-count (count incidents)
     :incidents incidents}))

(defn request [organization api-key]
  {:method "GET"
   :url (url organization)
   :headers {"Authorization" (format "Token token=%s" api-key)}})
