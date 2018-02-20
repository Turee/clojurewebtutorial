(ns clojurewebtutorial.components.tutorial.getting-started
  (:require [clojure.walk :as walk]))

(defn step [s]
  [:li
   [:pre s]])

(defn steps [& ss]
  [:ul
   (map step ss)])

(defn getting-started []
  (let []
    (fn []
      [:div
       [:h1 "Welcome!"]
       [:h2 "Getting started"]
       [:h3 "Project template"]
       (steps
         "lein new figwheel clojurewebtutorial"

         "Create directories clj and cljs under src, move clojurewebtutorial to cljs-dir"

         "Fix source paths at project.clj (clojurescript build to src/cljs and leiningen source-path to src/clj)"

         "Add dependencies to project.clj"

         "Let's mount reagent at core.cljs"

         "Fire up figwheel in repl. (fig-start)"

         )

       [:h3 "Backend structure"]
       (steps
         "Define some components in system.clj"

         "Configure them using config.edn"

         "Write a function that reads config.edn and fires up the system (in main.clj and user.clj) \n
          see repl configuration at user.clj"

         "Define middleware in handle.clj"

         "Define index route in handler.clj")

       [:h3 "Pizza order api"]
       (steps
         "Create migrations using create-migration in user.clj"

         "Create database layer db.clj"

         "Create handler.clj"

         )
       ])))
