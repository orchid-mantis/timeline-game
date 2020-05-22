(ns timeline-game.dropdown-panel
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf]))

(defn view [title header content]
  (let [s (reagent/atom {:open false})]
    (fn [title header content]
      (let [open? (:open @s)]
        [:div.dropdown {:style {:float :left}}
         [:div.dropbtn {:on-click #(swap! s update :open not)}
          [:div {:style {:float :left}}
           [:i.arrow {:class (if open? :down :right)}]]
          [:span title]
          header]

         [:div.dropdown-content {:style {:display (if open? :block :none)}}
          content]]))))
