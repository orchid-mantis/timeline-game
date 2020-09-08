(ns timeline-game.ui.game-result.views
  (:require
   [re-frame.core :as rf]))

;; -- Subscriptions -----------------------------------------------------------

(rf/reg-sub
 :game-result
 (fn [db]
   (get-in db [:game :result])))

(rf/reg-sub
 :game-result/show?
 (fn [db]
   (get-in db [:game :show-result?])))

(rf/reg-sub
 :game-result/view-data
 (fn []
   (rf/subscribe [:game-result]))
 (fn [result]
   (case result
     :player-won {:text "You won!" :color :green}
     :player-lost {:text "Game over" :color :red}
     nil)))

;; -- Events ------------------------------------------------------------------

(rf/reg-event-db
 :game-result/close
 (fn [db]
   (assoc-in db [:game :show-result?] false)))

;; -- UI ------------------------------------------------------------------

(defn view []
  (let [show? (rf/subscribe [:game-result/show?])
        view-data (rf/subscribe [:game-result/view-data])]
    (fn []
      [:div {:style {:position :fixed
                     :display (if @show? :block :none)
                     :width "100%"
                     :height "100%"
                     :top 0
                     :left 0
                     :right 0
                     :bottom 0
                     :background-color "rgba(255,255,255,0.9)"
                     :z-index 2}}
       [:div {:style {:position :absolute
                      :top "40%"
                      :left "50%"
                      :transform "translate(-50%,-50%)"
                      :-ms-transform "translate(-50%,-50%)"}}
        [:h1 {:style {:font-size "50px"
                      :color (:color @view-data)}}
         (:text @view-data)]

        [:button.btn
         {:on-click #(rf/dispatch [:new-game])}
         "Start a new game"]

        [:button.btn
         {:on-click #(rf/dispatch [:game-result/close])}
         "Close"]]])))
