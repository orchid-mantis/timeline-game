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
  (let [href (if show-face? "#card-front" "#card-back")]
    [:div.parent
     {:class class
      :style style}

     [:svg.card-border
      [:use
       {:class (when show-face? :card-front)
        :href href}]]

     [:div.card-border-clip
      [:img.image {:src (str "images/cards/" (:img-name card) ".png")}]
      [:div.content
       [:h3.card-title (:title card)]]]]))
