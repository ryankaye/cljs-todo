(ns todo.app.components.todo
  (:require [todo.app.state :as state]))


;; Add a new todo
;; Swap! to amend atom merge to amend the map 


(defn add-todo [new-todo]
  (swap! state/data update :todos merge
         {(get-in new-todo [:todoid]) new-todo}))


;; Update an existing todo
;; Swap! to amend atom assoc-in to amend the map 


(defn update-todo [e todoid]
  (if (= (-> e .-target .-type) "date")
    (swap! state/data update :todos assoc-in [todoid :date] (-> e .-target .-value))
    (swap! state/data update :todos assoc-in [todoid :item] (-> e .-target .-value))))


;; Delete a todo
;; Swap! to amend atom dissoc to amend the map 


(defn delete-todo [todoid]
  (swap! state/data update :todos dissoc todoid))


;; Component: Add Form 


(defn todo-add-form []
  [:div.add-form
   [:input.add-input {:type :text
                      :placeholder "Add a new todo item"
                      :value  (get (get @state/data :new-todo) :item)
                      :on-key-up (fn [e]
                                   (if (and (not= (get (get @state/data :new-todo) :item) "") (= (-> e .-which) 13))
                                     (do (add-todo (get @state/data :new-todo))
                                         (swap! state/data update :new-todo assoc-in [:todoid] (inc (get-in (get @state/data :new-todo) [:todoid])))
                                         (swap! state/data update :new-todo assoc-in [:date] "")
                                         (swap! state/data update :new-todo assoc-in [:item] ""))))
                      :on-change (fn [e] (swap! state/data update :new-todo assoc-in [:item] (-> e .-target .-value)))}]
   [:input {:type :date
            :value  (get (get @state/data :new-todo) :date)
            :on-change (fn [e] (swap! state/data update :new-todo assoc-in [:date] (-> e .-target .-value)))}]
   [:button {:data-tooltip "Add a new todo item"
             :disabled (if (= (get (get @state/data :new-todo) :item) "") "disabled")
             :on-click (fn []
                         (add-todo (get @state/data :new-todo))
                         (swap! state/data update :new-todo assoc-in [:todoid] (inc (get-in (get @state/data :new-todo) [:todoid])))
                         (swap! state/data update :new-todo assoc-in [:date] "")
                         (swap! state/data update :new-todo assoc-in [:item] ""))} "Add"]])



;; Component: Update form + listing


(defn todo-update-form []
  [:ul.todo-list

   (map (fn [{:keys [todoid item date]}]
          [:li {:key todoid :class (if (> (.now js/Date) (.parse js/Date date)) "overdue" "")}
           [:input {:type :radio
                    :data-tooltip "Delete todo"
                    :on-click (fn []
                                (delete-todo todoid))}]
           [:input {:value item
                    :type :text
                    :on-change (fn [e]
                                 (update-todo e todoid))}]
           [:input {:type :date
                    :value date
                    :on-change (fn [e]
                                 (update-todo e todoid))}]])
        (vals (reverse (get @state/data :todos))))])


;; Component: Main views renderer


(defn todo []
  [:main
   [:div.todo-form
    [todo-add-form]
    [todo-update-form]]])