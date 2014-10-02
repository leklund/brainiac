(ns brainiac.plugins.wmata-train-tracker
  (:import [java.text SimpleDateFormat]
           [java.util TimeZone])
  (:use [clojure.contrib.json :only (read-json)]
        [clojure.java.io :only (reader)])
  (:require [brainiac.plugin :as brainiac]))

(def time-format
  (let [date-format (SimpleDateFormat. "yyyyMMdd HH:mm:ss")]
    (.setTimeZone date-format (TimeZone/getTimeZone "America/NewYork"))
    date-format))

(defn now [] (System/currentTimeMillis))

(defn due-in-minutes [due-in-millis]
  (if (< due-in-millis (* 60 1000))
    "DUE"
    (str (int (/ due-in-millis (* 60 1000))) " MIN")))

(def destination-translations
  {"OR" "Orange"
   "GR" "Green"
   "BL" "Blue"
   "YL" "Yellow"
   "RD" "Red"
   "SV" "Silver"})

(defn parse-wmata [node]
  {
    :due (:Min node)
    :dest (:DestinationName node)
    :cars (:Car node)
    :line (get destination-translations (:Line node))
   })

(defn transform [stream]
  (let [json (read-json (reader stream))
        trains (:Trains json)]
  (assoc {}
        :name "wmata-train-tracker"
        :station (:LocationName (nth trains 0))
        :data (map parse-wmata (take 7 trains)))))

(defn url [station api-key]
  (format "http://api.wmata.com/StationPrediction.svc/json/GetPrediction/%s?api_key=%s" station api-key))

(defn configure [{:keys [program-name station api-key]}]
  (brainiac/simple-http-plugin
    {:method "GET" :url (url station api-key)}
    transform program-name))

