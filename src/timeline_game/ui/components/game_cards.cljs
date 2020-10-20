(ns timeline-game.ui.components.game-cards
  (:require
   [re-frame.core :as rf]
   [reagent.core :as reagent]))

(defn basic-card [card show-face? class style]
  (fn [card show-face? class style]
    [:figure.card.card--normal
     {:class class
      :style style}
     [:figcaption.card__caption
      [:h1.card__title (:title card)]
      (when show-face? [:h3.card__time-desc (:time-desc card)])]]))

(defn image-card [card show-face? class style]
  (let [card-id (:id card)
        scroll-text-id (str "text-" card-id)]
    (reagent/create-class
     {:reagent-render
      (fn [card show-face? class style]
        (let [href (if show-face? "#card-front" "#card-back")]
          [:div.parent
           {:class class
            :style style}

           [:svg.card-border
            [:use
             {:class (when show-face? :card-front)
              :href href}]]

           (when show-face?
             [:div.scroll-text
              {:id scroll-text-id}
              (:time-desc card)])

           [:div.card-border-clip
            [:img.image {:src (str "images/cards/" (:img-name card) ".png")}]
            [:div.content
             [:h3.card-title (:title card)]]]]))

      :component-did-mount
      (fn []
        (when show-face?
          (rf/dispatch [:warp-scroll-text (str "#" scroll-text-id)])))})))

(defn warp-text [targets]
  (js/cssWarp (clj->js {:path [[35.2688 216.7838]
                               [38.2112 217.7934 42.4588 220.438 50.7617 219.9646]
                               [76.0438 218.5233 94.8823 196.9491 123.872 202.8383]]
                        :targets targets
                        :rotationMode "skew"
                        :indent "1em"
                        ;; :showPath {:color "red"
                        ;;            :thickness 1}
                        })))

(rf/reg-event-fx
 :warp-scroll-text
 (fn [{:keys [db]} [_ targets]]
   {:db db
    :warp-text [targets]}))

(rf/reg-fx
 :warp-text
 (fn [[targets]]
   (warp-text targets)))