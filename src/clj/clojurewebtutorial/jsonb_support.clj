(ns clojurewebtutorial.jsonb-support
  (:require [clojure.java.jdbc :as jdbc]
            [cheshire.core :as json])
  (:import org.postgresql.util.PGobject))

(defrecord JsonPrimitive [value])

(defn- to-psql-jsonb [value]
  (doto (PGobject.)
    (.setType "jsonb")
    (.setValue (json/generate-string value))))

(extend-protocol jdbc/ISQLValue
  clojure.lang.IPersistentMap
  (sql-value [value]
    (to-psql-jsonb value))

  clojure.lang.Seqable
  (sql-value [value]
    (to-psql-jsonb value))

  JsonPrimitive
  (sql-value [value]
    (to-psql-jsonb (:value value))))

(extend-protocol jdbc/IResultSetReadColumn
  java.sql.Array
  (result-set-read-column [val _ _]
    (into [] (.getArray val)))
  PGobject
  (result-set-read-column [pgobj metadata idx]
    (let [type (.getType pgobj)
          value (.getValue pgobj)]
      (case type
        "jsonb" (json/parse-string value true)
        :else value))))

(defn json
  "Specify that you want to insert a value into a json column"
  [value]
  (->JsonPrimitive value))
