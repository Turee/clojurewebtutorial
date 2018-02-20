(ns clojurewebtutorial.core
  (:require [clojurewebtutorial.components.tutorial.getting-started :as gs]
            [reagent.core :as reagent]))

(enable-console-print!)

(println "This text is printed from src/clojurewebtutorial/core.cljs. Go ahead and edit it and see reloading in action.")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Hello world!"}))


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )

; Mount root component
(reagent/render [gs/getting-started] (js/document.getElementById "app"))
