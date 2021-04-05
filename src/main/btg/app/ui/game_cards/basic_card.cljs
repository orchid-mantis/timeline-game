(ns btg.app.ui.game-cards.basic-card)

(def card-width 166)

(def theme
  {:theme
   {:ribbon
    {:color "crimson"
     :width "200px"
     :left "-180px"}}})

(defn view [card show-face? attributes]
  [:figure.card.card--normal
   attributes
   [:figcaption.card__caption
    [:h1.card__title (:title card)]
    (when show-face?
      [:h3.card__time-desc (:time-desc card)])]])
