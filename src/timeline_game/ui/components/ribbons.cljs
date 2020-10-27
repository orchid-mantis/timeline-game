(ns timeline-game.ui.components.ribbons
  (:require
   [dv.cljs-emotion-reagent :refer [defstyled]]))

(defstyled ribbon :div
  (fn [{:keys [theme hide]}]
    (let [color              (get-in theme [:ribbon :color])
          border             (str "15px solid " color)
          transparent-border (str "15px solid transparent")]
      {:position :absolute
       :top "25px"
       :left "-180px"
       :width "200px"
       :height "30px"
       :background color
       :z-index -1

       :visibility (when hide :hidden)
       ".scroll-item:first-of-type &" {:visibility :hidden}

       ":before" {:content "''"
                  :position :absolute
                  :top 0
                  :left "-20px"
                  :border-left transparent-border
                  :border-right border
                  :border-top border
                  :border-bottom border}

       ":after"  {:content "''"
                  :position :absolute
                  :top 0
                  :right "-30px"
                  :border transparent-border
                  :border-left border}})))

(defn timeline-ribbon [highlight-drop-zones?]
  (ribbon
   {:hide highlight-drop-zones?}
   []))