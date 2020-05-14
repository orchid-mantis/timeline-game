(ns timeline-game.game-result
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 :game-result
 (fn [db]
   (get-in db [:game :result])))

(defn view []
  (let [game-result (rf/subscribe [:game-result])]
    (fn []
      (let [show? (not= @game-result :await)
            display-data (case @game-result
                           :player-won {:text "You won!" :color :green}
                           :player-lost {:text "Game over!" :color :red}
                           nil)]
        [:div {:style {:position :fixed
                       :display (if show? :block :none)
                       :width "100%"
                       :height "100%"
                       :top 0
                       :left 0
                       :right 0
                       :bottom 0
                       :background-color "rgba(255,255,255,0.9)"
                       :z-index 2}}
         [:div {:style {:position :absolute
                        :top "50%"
                        :left "50%"
                        :font-size "50px"
                        :color (:color display-data)
                        :transform "translate(-50%,-50%)"
                        :-ms-transform "translate(-50%,-50%)"}}
          (:text display-data)]]))))
