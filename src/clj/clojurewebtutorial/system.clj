(ns clojurewebtutorial.system
  (:require [hikari-cp.core :as hikari]
            [clojurewebtutorial.handler :as h]
            [org.httpkit.server :as server]
            [clojurewebtutorial.jsonb-support]
            [integrant.core :as integrant]
            [migratus.core :as mig]))

; Hikari connection pool
(defmethod integrant/init-key :datasource/hikari-cp [_ {:keys [config]}]
  {:datasource (hikari/make-datasource config)})
(defmethod integrant/halt-key! :datasource/hikari-cp [_ db-spec]
  (hikari/close-datasource (:datasource db-spec)))

; Web server
(defmethod integrant/init-key :server/http-kit [_ {:keys [config handler]}]
  (server/run-server handler config))
(defmethod integrant/halt-key! :server/http-kit [_ stop-function]
  (stop-function))

; Handler
(defmethod integrant/init-key :handler [_ context]
  (h/make-handler context))

; Migrations
(defmethod integrant/init-key :migratus [_ {:keys [config options] :as component}]
  (when (:migrate-on-init options)
    (mig/migrate config))
  component)
