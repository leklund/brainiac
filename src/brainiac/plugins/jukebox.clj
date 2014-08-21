(ns brainiac.plugins.jukebox
  (:import [java.text SimpleDateFormat]
           [java.util TimeZone])
  (:use [clojure.contrib.json :only (read-json)]
        [clojure.java.io :only (reader)])
  (:require [brainiac.plugin :as brainiac]))

(defn fix-default-artwork [artwork-url jukebox-url]
  (if (re-matches #"^/.*" artwork-url)
    (str jukebox-url artwork-url)
    artwork-url))

(defn transform [stream]
  (let [json (first (:track (:recenttracks (read-json (reader stream)))))
        artist (:#text (:artist json))
        album (:#text (:album json))
        title (:name json)
        artwork (:#text (last (:image json)))]
  { :name "jukebox"
    :artist artist
    :album album
    :title title
    :artwork artwork }))

(defn current-track-url [user api-key]
  (format "http://ws.audioscrobbler.com/2.0/?method=user.getrecenttracks&user=%s&limit=2&format=json&api_key=%s" user api-key))

(defn configure [{:keys [program-name user api-key]}]
  (brainiac/simple-http-plugin
    {:url (current-track-url user api-key) }
    transform program-name))
