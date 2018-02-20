(ns clojurewebtutorial.features.pizza.handler
  (:require [compojure.api.core :refer [context GET POST]]
            [schema.core :as sc]
            [clojurewebtutorial.features.pizza.db :as db]
            [ring.util.http-response :as hr]))


(defn routes [{:keys [db]}]
  (context "/pizza" []
    (GET "/" []
      (hr/ok (db/list-pizza db)))
    (POST "/" []
      :body [pizza sc/Any]
      (hr/ok (db/create-pizza db pizza)))))
