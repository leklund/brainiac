(ns brainiac.pages.root
  (:require [brainiac.plugin :as brainiac])
  (:use [noir.core :only (defpage)]
        [hiccup.core]
        [brainiac.loader]
        [clojure.tools.logging :only (info warn)]
        [brainiac.pages.layout :only (main-layout plugins)]))

(defpage "/" []
  (main-layout [:div#plugins (plugins)]))

(defpage [:post "/message/:program"] {:keys [program plugin message]}
  (when (contains? (into #{} @loaded) (brainiac/fullname plugin))
    (brainiac/receive-message program plugin message)
    {:status 201
     :body ""}))
