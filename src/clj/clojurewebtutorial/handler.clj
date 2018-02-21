(ns clojurewebtutorial.handler
  (:require [compojure.api.core :as api]
            [compojure.api.sweet :as sweet]
            [ring.middleware.defaults :as defaults]
            [clojurewebtutorial.features.pizza.handler :as pizza]
            [clojurewebtutorial.graphql.handler :as graphql]
            [cheshire.core]
            [ring.middleware.json :as json-mw]
            [ring.util.http-response :as hr]))

; This is example of a middleware
(defn example-middleware [handler]
  (fn [req]
    (println "Processing request" (:request-method req) (:uri req))
    (time (handler req))))

(defn middleware [handler {:keys [ring-defaults]}]
  (-> handler
      (defaults/wrap-defaults ring-defaults)                ;Sane default middleware
      (json-mw/wrap-json-response)                          ;Converts clojure data to JSON and back
      (example-middleware)))                                ;Just and example :)

(defn make-handler [context]
  (-> (sweet/api
        {:ui   "/api-docs"
         :spec "/swagger.json"}
        (api/GET "/" []                                     ;Index route
          (merge
            (hr/resource-response "public/index.html")
            {:headers {"Content-Type" "text/html"}}))
        (api/context "/api/v1" []
          (pizza/routes context))
        (api/context "/graphql" []
          (graphql/routes context)))
      (middleware context)))
