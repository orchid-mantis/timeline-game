(ns btg.app.ui.components.icons)

(defn well-played []
  [:i.fas.fa-check {:style {:color :green}}])

(defn wrong-played []
  [:i.fas.fa-times {:style {:color :red}}])

(defn card-deck []
  [:i.fas.fa-clone {:style {:color :black}}])

(defn caret-right []
  [:i.fas.fa-caret-right {:style {:color :black}}])

(defn hourglass-animated []
  [:div.hourglass.fa-stack
   [:i.fas.fa-stack-1x.fa-hourglass-start]
   [:i.fas.fa-stack-1x.fa-hourglass-half]
   [:i.fas.fa-stack-1x.fa-hourglass-end]
   [:i.fas.fa-stack-1x.fa-hourglass-end.spin]])
