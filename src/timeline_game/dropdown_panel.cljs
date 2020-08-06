(ns timeline-game.dropdown-panel
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf]))

(defn view [title content]
  (let [s (reagent/atom {:open false})]
    (fn [title content]
      (let [open? (:open @s)]
        [:div.dropdown
         [:div.button {:on-click #(swap! s update :open not)}
          [:i.arrow {:class (if open? :down :right)}]
          [:span title]]

         [:div.dropdown-content {:style {:display (if open? :block :none)}}
          content]]))))
