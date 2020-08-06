(ns timeline-game.overlay
  (:require [re-frame.core :as rf]))

(rf/reg-event-db
 :overlay/toggle
 (fn [db [_ overlay-id]]
   (update-in db [:overlay :show? overlay-id] (fnil not false))))

(rf/reg-sub
 :overlay/show?
 (fn [db [_ overlay-id]]
   (get-in db [:overlay :show? overlay-id])))

(defn view [overlay-id content]
  (let [show? (rf/subscribe [:overlay/show? overlay-id])]
    (fn [overlay-id content]
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
        content]])))