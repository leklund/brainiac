(ns brainiac.plugins.dark-sky
  (:require [brainiac.plugin :as brainiac]
            [clojure.contrib.json :as json :only (read-json)]
            [clojure.java.io :as io :only (reader)]
            [clojure.contrib.string :as string]))


(defn format-forecast [forecast]
  {
    :temp (int (:temperature (:currently forecast)))
    :current-summary (:summary (:currently forecast))
    :hour-summary (:summary (:minutely forecast))
  })

(defn transform [stream]
  (let [json (json/read-json (io/reader stream))]
    (assoc {}
      :name "dark-sky"
      :title "Right now, outside..."
      :data (format-forecast json))))

(defn url [api-key lat lon]
  (format "https://api.forecast.io/forecast/%s/%s,%s" api-key lat lon))

(defn configure [{:keys [api-key lat lon program-name]}]
  (brainiac/simple-http-plugin
    {:url (url api-key lat lon)}
    transform program-name))
