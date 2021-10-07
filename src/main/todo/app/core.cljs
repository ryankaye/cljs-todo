(ns todo.app.core
  (:require [reagent.dom :as rdom]
            [todo.app.components.header :refer [header]]
            [todo.app.components.todo :refer [todo]]
            [todo.app.components.footer :refer [footer]]))

(defn app
  []
  [:div.container
   [header]
   [todo]
   [footer]])

(defn render []
  (rdom/render [app] (.getElementById js/document "root")))

(defn ^:export main []
  (render))

(defn ^:dev/after-load reload! []
  (render))
