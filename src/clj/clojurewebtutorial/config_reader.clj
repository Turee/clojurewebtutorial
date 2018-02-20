(ns clojurewebtutorial.config-reader
  (:require [integrant.core :as ig]
            [aero.core :refer [read-config]]
            [clojure.string :as str]
            [clojure.java.io :as io]))


(def ^:private config-file "config.edn")

(defmethod aero.core/reader 'ig/ref
  [opts tag value]
  (ig/ref value))

(defn component-configurations
  "Returns a { component -> configuration } mapping."
  []
  (->> (read-config (io/resource config-file))
       :system))
(comment
  (component-configurations))