(ns clojurewebtutorial.graphql.handler
  (:require [compojure.api.core :refer [context]]
            [compojure.api.sweet :refer [resource]]
            [com.walmartlabs.lacinia :as l]
            [ring.util.http-response :as hr]
            [clojure.spec.alpha :as s]))

(s/def ::query string?)

(defn- handle-query
  [{:keys [params]} {:keys [graphql] :as ctx}]
  (let [schema (:schema graphql)
        query (:query params)]

    (hr/ok (l/execute schema query nil ctx))))

(defn routes [ctx]
  (context "/" []
    (resource
      {:coercion :spec
       :post     {:parameters {:query-params (s/keys :opt-un [::query])}}
       :handler  #(handle-query % ctx)})))