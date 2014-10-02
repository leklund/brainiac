(ns brainiac.plugins.slideshow
  (:use [clojure.contrib.json :only (read-json)]
        [clojure.java.io :only (reader)])
  (:require [brainiac.plugin :as brainiac])
)

(defn get-photos [photoJson] {
  :url (:url photoJson)
  :playspace_name (:playspace_name photoJson)
  :playspace_city (:playspace_city photoJson)
  :playspace_state (:playspace_state photoJson)
})

(defn transform [stream]
  (let [json (read-json (reader stream))]
    (assoc {}
      :name "slideshow"
      :data (map get-photos (:photos json)))))

(defn configure [{:keys [url program-name]}]
  (brainiac/simple-http-plugin
    {:url url} transform program-name))
