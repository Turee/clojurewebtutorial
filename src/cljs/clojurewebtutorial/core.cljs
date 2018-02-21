(ns clojurewebtutorial.core
  (:require
    [clojurewebtutorial.components.pizza :as pizza]
    [day8.re-frame.http-fx]
    [reagent.core :as reagent]))

(enable-console-print!)


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )

; Mount root component
(reagent/render [:div {}
                 [pizza/pizza-page]] (js/document.getElementById "app"))
