(ns todo.app.components.footer)

(defn footer []
  [:footer
   [:p "Built with ClojureScript Reagent."
    [:a {:href "https://github.com/ryankaye/cljs-todo"} "View source code"]]])