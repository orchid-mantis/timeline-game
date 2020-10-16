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

   (if show-face?
     [:svg.card-border
      [:use.card-front
       {:href "#card-front"}]]

     [:svg.card-border
      [:use
       {:href "#card-back"}]])

   [:div.card-border-clip
    [:img.image {:src (str "images/cards/" (:img-name card) ".png")}]
    [:div.content
     [:h3.card-title (:title card)]]]])
