(ns brainiac.plugins.instagram-tag
  (:use [clojure.contrib.json :only (read-json)]
        [clojure.java.io :only (reader)])
  (:require [brainiac.plugin :as brainiac]
            [clojure.contrib.string :as string])
)

(def tag-query (atom ""))

(defn get-photos [photoJson] {
  :url (:url (:standard_resolution (:images photoJson)))
  :username (:username (:user photoJson))
})

(defn transform [stream]
  (let [json (read-json (reader stream))]
    (assoc {}
      :name "instagram-tag"
      :query @tag-query
      :data (map get-photos (:data json)))))

(defn url [tag access_token]
  (format "https://api.instagram.com/v1/tags/%s/media/recent?access_token=%s" tag access_token))

(defn configure [{:keys [tag access_token program-name]}]
  (swap! tag-query str tag)
  (brainiac/simple-http-plugin
    {:url (url tag access_token) }
    transform program-name))

