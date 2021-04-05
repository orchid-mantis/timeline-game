(ns btg.app.ui.game-cards.image-card
  (:require
   [reagent.core :as reagent]
   [btg.app.ui.utils :as utils]))

(def card-width 160)

(def theme
  {:theme
   {:ribbon
    {:color "#e4e0d5"
     :width "210px"
     :left "-180px"}}})

(defn warp-text [target {:keys [indent css]}]
  (js/cssWarp (clj->js {:path [[35.2688 216.7838]
                               [38.2112 217.7934 42.4588 220.438 50.7617 219.9646]
                               [76.0438 218.5233 94.8823 196.9491 123.872 202.8383]]
                        :targets target
                        :rotationMode "skew"
                        :indent indent
                        :css css
                        ;; :showPath {:color "red"
                        ;;            :thickness 1}
                        })))

(defn warp-text-options [{:keys [font-size indent top] :as opts}]
  (if (map? opts)
    {:indent indent
     :css (str "font-size: " font-size "; top: " top ";")}
    {:indent "0"
     :css ""}))

(defn view [card show-face? attributes]
  (let [card-id (:id card)
        scroll-text-id (str "text-" card-id)
        options (warp-text-options (:scroll-text card))]
    (reagent/create-class
     {:reagent-render
      (fn [card show-face? attributes]
        (let [{:keys [class style]} attributes
              width 160
              height 264]
          [:div.parent
           (merge attributes
                  {:class (utils/cs class
                                    (if show-face? :card-front :card-back))
                   :style (merge style
                                 {:width width
                                  :height height})})
           [:svg.card-border
            [:use
             {:href (if show-face? "#card-front" "#card-back")}]]

           (when show-face?
             [:div.scroll-text
              {:id scroll-text-id}
              (:time-desc card)])

           [:div.card-border-clip
            {:style {:width (- width 2)
                     :height (- height 2)
                     :top 2
                     :left 2}}
            [:img.image {:src (str "images/cards/" (:img-name card) ".png")}]
            [:div.content
             [:h3.card-title (:title card)]]]]))

      :component-did-update
      (fn []
        (when show-face?
          (warp-text (str "#" scroll-text-id) options)))

      :component-did-mount
      (fn []
        (when show-face?
          (warp-text (str "#" scroll-text-id) options)))})))
