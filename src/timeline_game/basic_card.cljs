(ns timeline-game.basic-card
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf]))

(defn view [card show-face? class style]
  (fn [card show-face? class style]
    [:figure.card.card--normal
     {:class class
      :style style}
     [:figcaption.card__caption
      [:h1.card__title (:title card)]
      (when show-face? [:h3.card__time-desc (:time-desc card)])]]))
