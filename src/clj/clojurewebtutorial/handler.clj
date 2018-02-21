(ns clojurewebtutorial.handler
  (:require [compojure.api.core :as api]
            [compojure.api.sweet :as sweet]
            [ring.middleware.defaults :as defaults]
            [clojurewebtutorial.features.pizza.handler :as pizza]
            [clojurewebtutorial.graphql.handler :as graphql]
            [cheshire.core]
            [ring.middleware.json :as json-mw]
            [ring.util.http-response :as hr]))

(defn middleware [handler {:keys [ring-defaults]}]
  (-> handler
      (defaults/wrap-defaults ring-defaults)                ;Sane default middleware
      (json-mw/wrap-json-response)))                        ;Converts clojure data to JSON and back

(defn make-handler [context]
  (-> (sweet/api
        {:ui   "/api-docs"
         :spec "/swagger.json"
         :data {:info     {:title       "Sample API"
                           :description "Compojure Api example"}
                :tags     [{:name "api", :description "some apis"}]
                :consumes ["application/json"]
                :produces ["application/json"]}}
        (api/GET "/" []                                     ;Index route
          (merge
            (hr/resource-response "public/index.html")
            {:headers {"Content-Type" "text/html"}}))
        (api/context "/api/v1" []
          (pizza/routes context))
        (api/context "/graphql" []
          (graphql/routes context)))
      (middleware context)))

(comment
  (map (fn [a] (apply merge (second a)))
       (group-by :asdf [{:asdf 32 :c 4} {:asdf 32 :b 3} {:asdf 2}])))