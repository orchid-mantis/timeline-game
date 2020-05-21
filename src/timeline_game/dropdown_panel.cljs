(ns timeline-game.dropdown-panel
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf]))

(defn view [title header content]
  (let [s (reagent/atom {:open false})]
    (fn [title header content]
      (let [open? (:open @s)]
        [:div.dropdown {:style {:float :left}}
         [:div.dropbtn {:on-click #(swap! s update :open not)
                        :style {:margin "5px"
                                :padding "10px"}}

          [:div {:style {:width "30px"
                         :height "30px"
                         :display :inline-block
                         :margin-right "10px"
                         :float :left}}
           [:i.arrow {:class (if open? :down :right)}]]
          [:span title]
          header]

         [:div.dropdown-content {:style {:display (if open? :block :none)
                                         :left 0}}
          content]]))))
