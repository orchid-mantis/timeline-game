(ns timeline-game.ui.components.game-cards)

(defn basic-card [card show-face? class style]
  (fn [card show-face? class style]
    [:figure.card.card--normal
     {:class class
      :style style}
     [:figcaption.card__caption
      [:h1.card__title (:title card)]
      (when show-face? [:h3.card__time-desc (:time-desc card)])]]))

(defn image-card [card show-face? class style]
  [:div.parent
   {:class class
    :style style}
   [:img.border {:src "images/cards/card-border-back.svg"}]
   [:img.image {:src "images/cards/adam-eva.png"}]
   [:div.content
    [:h3.card-title (:title card)]]])