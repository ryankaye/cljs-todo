(ns todo.app.components.todo
  (:require [todo.app.state :as state])
  (:require [reagent.core :as r]))

;; Add a new todo

(defn add-todo [vals]
  (swap! state/todos merge
         {(get-in @vals [:uid]) {:todoid (get-in @vals [:uid]) :item (get-in @vals [:new-input])}}))

;; Update an existing todo

(defn update-todo [e todoid]
  (swap! state/todos assoc-in [todoid :item] (-> e .-target .-value)))

;; Delete a todo

(defn delete-todo [todoid]
  (swap! state/todos dissoc todoid todoid))

;; Component: Add Form with local state

(defn todo-form []
  (let [new-todo (r/atom {:new-input "" :uid 10})]
    (fn []
      [:div
       [:input {:placeholder "Add a new todo item"
                :value  (get @new-todo :new-input)
                :on-key-up (fn [e]
                             (if (and (not= (get @new-todo [:new-input]) "") (= (-> e .-which) 13))
                               (do (add-todo new-todo)
                                   (swap! new-todo assoc-in [:uid] (inc (get-in @new-todo [:uid])))
                                   (swap! new-todo assoc-in [:new-input] ""))))
                :on-change (fn [e] (swap! new-todo assoc-in [:new-input] (-> e .-target .-value)))}]
       [:button {:data-tooltip "Add a new todo item"
                 :disabled (if (= (get @new-todo :new-input) "") "disabled")
                 :on-click (fn []
                             (add-todo new-todo)
                             (swap! new-todo assoc-in [:uid] (inc (get-in @new-todo [:uid])))
                             (swap! new-todo assoc-in [:new-input] ""))} "Add"]])))

;; Component: Listing + update

(defn todo-listing []
  [:ul.todo-list
   (map (fn [{:keys [todoid item]}]
          [:li {:key todoid}
           [:button {:data-tooltip "Delete todo"
                     :on-click (fn []
                                 (delete-todo todoid))} " x "]
           [:input {:value item
                    :on-change (fn [e]
                                 (update-todo e todoid))}]])
        (vals (reverse @state/todos)))])

;; Component: Main views renderer

(defn todo []
  [:main
   [:div.todo-form
    [todo-form]
    [todo-listing]]])