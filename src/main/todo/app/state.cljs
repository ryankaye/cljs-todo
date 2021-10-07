(ns todo.app.state
  (:require [reagent.core :as r]))

(def todos (r/atom {7 {:todoid 7
                       :item "Go to the mattresses"
                       :completed false}
                    8 {:todoid 8
                       :item "Take the cannoli"
                       :completed false}
                    9 {:todoid 9
                       :item "Leave the gun"
                       :completed false}}))

