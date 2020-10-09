(ns timeline-game.ui.components.views)

(defn button [{:keys [on-click label enabled] :as props}]
  (let [disabled? ((fnil not true) enabled)]
    [:a.button
     (merge
      {:class (when disabled? :disabled)}
      (when on-click
        {:on-click (fn []
                     (when (not disabled?)
                       (on-click)))})
      (dissoc props :enabled :on-click))
     label]))
