(ns timeline-game.ui.components.views)

(defn button [{:keys [on-click label] :as props}]
  [:a.button
   (merge
    (when on-click
      {:on-click (fn []
                   (on-click))})
    props)
   label])
