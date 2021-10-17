(ns todo.app.state
  (:require [reagent.core :as r]))

(def data (r/atom {:new-todo {:todoid 10 :item "" :date ""}
                   :todos {7 {:todoid 7
                              :item "Go to the mattresses"
                              :date "2021-10-07"}
                           8 {:todoid 8
                              :item "Take the cannoli"
                              :date "2021-11-08"}
                           9 {:todoid 9
                              :item "Leave the gun"
                              :date "2021-12-09"}}}))

