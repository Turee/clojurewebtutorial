(ns clojurewebtutorial.main
  (:require [integrant.core :as integrant]
            [clojure.tools.logging :as log]
            [clojurewebtutorial.config-reader :as config]
            [clojurewebtutorial.system]))

(defn -main [& args]
  (let [config (config/component-configurations)]
    (try
      (integrant/init config)
      (catch Exception e
        (integrant/halt! (:system (ex-data e)))
        (log/fatal e "Failed to start system, terminating...")
        (System/exit 1)))))