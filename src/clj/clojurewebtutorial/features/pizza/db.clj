(ns clojurewebtutorial.features.pizza.db
  (:require [honeysql.helpers :as hh]
            [honeysql.core :as sql]
            [clojure.java.jdbc :as jdbc]))

(defn- pizza-query []
  (-> (hh/select :*)
      (hh/from :pizza)))



; API
(defn list-pizza [db]
  (->> (pizza-query)
       (sql/format)
       (jdbc/query db)))

(defn create-pizza [db pizza]
  (jdbc/insert! db :pizza {:data pizza}))

(comment
  (pizza-query))