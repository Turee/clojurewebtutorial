(ns clojurewebtutorial.components.pizza
  (:require [re-frame.core :as rf]
            [ajax.core :as ajax]))

(rf/reg-event-db :handle-list-pizza
                 (fn [db [_ pizzas]]
                   (assoc db :pizzas pizzas)))

(rf/reg-event-fx :api/list-pizza
                 (fn [_ _]
                   {:http-xhrio
                    {:uri             "/api/v1/pizza"
                     :method          :get
                     :response-format (ajax/json-response-format {:keywords? true}) ;; IMPORTANT!: You must provide this.
                     :on-success      [:handle-list-pizza]}}))

(rf/reg-sub :pizzas
            (fn [db]
              (:pizzas db)))


(defn pizza [{:keys [data id]}]
  [:div {:key id}
   [:b (:name data)]
   [:ul
    (map (fn [t] [:li t]) (:toppings data))]])

(defn pizza-page []
  (let [pizzas (rf/subscribe [:pizzas])]
    (rf/dispatch [:api/list-pizza])
    (fn []
      [:div
       [:h1 "Hello pizza"]
       (map pizza @pizzas)])))

