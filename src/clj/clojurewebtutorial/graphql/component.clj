(ns clojurewebtutorial.graphql.component
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [com.walmartlabs.lacinia.schema :as schema]
            [clojure.edn :as edn]
            [com.walmartlabs.lacinia.util :refer [attach-resolvers]]
            [clojurewebtutorial.features.pizza.db :as pizza]))

(defn make-schema
  "Reads schema from edn file, attaches resolves and compiles it, so that it can be used in endpoint."
  []
  (-> (io/resource "schema.edn")
      slurp
      edn/read-string
      (attach-resolvers {:find-pizzas pizza/find-pizzas})
      schema/compile))