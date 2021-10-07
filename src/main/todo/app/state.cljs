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


;; (def inputs (r/atom {:new-input ""
;;                      :uid 10}))

;; (def data (r/atom {:inputs {:new-input ""
;;                             :uid 10}}
;;                   {:todos {7 {:todoid 7
;;                               :item "Go to the mattresses"
;;                               :completed false}
;;                            8 {:todoid 8
;;                               :item "Take the cannoli"
;;                               :completed false}
;;                            9 {:todoid 9
;;                               :item "Leave the gun"
;;                               :completed false}}}))


;; (def todos (r/atom [{:todoid 7
;;                      :item "Learn ClojureScript"
;;                      :completed false}
;;                     {:todoid 8
;;                      :item "Learn Elm"
;;                      :completed false}
;;                     {:todoid 9
;;                      :item "Buy milk"
;;                      :completed false}]))
