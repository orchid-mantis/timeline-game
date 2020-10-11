(ns timeline-game.ui.components.views
  (:require
   [re-frame.core :as rf]
   [timeline-game.ui.components.icons :as icon]))

(defn button [{:keys [on-click label enabled] :as props}]
  (let [disabled? ((fnil not true) enabled)]
    [:a.button
     (merge
      {:class (when disabled? :disabled)}
      (when on-click
        {:on-click (fn []
                     (when (not disabled?)
                       (on-click)))})
      (dissoc props :enabled :on-click))
     label]))

(defn hourglass []
  (let [opponent-active? (rf/subscribe [:game/opponent-active?])]
    [:div {:style {:position :absolute
                   :left "50%"}}
     [:div {:style {:position :relative
                    :left "-50%"}}
      (when @opponent-active?
        [:div {:style {:font-size "4em"
                       :color "rgb(155, 127, 91)"}}
         [icon/hourglass-animated]])]]))