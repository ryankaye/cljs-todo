(ns todo.app.components.todo
  (:require [todo.app.state :as state]))


;; --- UPDATE


;; Add a new todo (Swap! to amend the atom; merge to amend the map)


(defn add-todo [new-todo]
  (swap! state/data update :todos merge
         {(get-in new-todo [:todoid]) new-todo}))


;; Update an existing todo (Swap! to amend the atom; assoc-in to amend the map)


(defn update-todo [e todoid]
  (swap! state/data update :todos assoc-in [todoid (keyword (-> e .-target .-name))] (-> e .-target .-value)))


;; Delete a todo (Swap! to amend the atom; dissoc to amend the map)


(defn delete-todo [todoid]
  (swap! state/data update :todos dissoc todoid))


;; Reset new-todo atom values ready for the next input


(defn reset-new-todo [last-id]
  (swap! state/data update :new-todo merge {:todoid (inc last-id) :date "" :text ""}))


;; --- VIEW 


;; Component: Add Form 


(defn todo-add-form []
  (let [{:keys [todoid text date]} (get @state/data :new-todo)]
    [:div.add-form
     [:input.add-input {:type :text
                        :placeholder "Add a new todo item "
                        :value text
                        :on-key-up (fn [e]
                                     (if (and (not= text "") (= (-> e .-which) 13))
                                       (do
                                         (add-todo (get @state/data :new-todo))
                                         (reset-new-todo todoid))))
                        :on-change (fn [e] (swap! state/data update :new-todo assoc-in [:text] (-> e .-target .-value)))}]
     [:input {:type :date
              :value  (get (get @state/data :new-todo) :date)
              :on-change (fn [e] (swap! state/data update :new-todo assoc-in [:date] (-> e .-target .-value)))}]
     [:button {:data-tooltip "Add a new todo item"
               :disabled (if (= text "") "disabled")
               :on-click (fn []
                           (add-todo (get @state/data :new-todo))
                           (reset-new-todo todoid))} "Add"]]))


;; Component: Update form + listing


(defn todo-update-form []
  [:ul.todo-list
   (map (fn [{:keys [todoid text date]}]
          [:li {:key todoid :class (if (> (.now js/Date) (.parse js/Date date)) "overdue" "")}
           [:input {:type :radio
                    :data-tooltip "Delete todo"
                    :on-click (fn []
                                (delete-todo todoid))}]
           [:input {:value text
                    :type :text
                    :name "text"
                    :on-change (fn [e]
                                 (update-todo e todoid))}]
           [:input {:type :date
                    :value date
                    :name "date"
                    :on-change (fn [e]
                                 (update-todo e todoid))}]])
        (vals (reverse (get @state/data :todos))))])


;; Component: Main view renderer


(defn todo []
  [:main
   [:div.todo-form
    [todo-add-form]
    [todo-update-form]]])