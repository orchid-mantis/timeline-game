(ns timeline-game.ui.timeline.views
  (:require
   [re-frame.core :as rf]
   [reagent.core :as reagent]
   [timeline-game.ui.timeline.events]
   [timeline-game.ui.timeline.subs]
   [timeline-game.ui.components :as uic]
   [timeline-game.ui.utils :as utils]))

(defn drop-zone [s pos highlight-drop-zones?]
  [:div.scroll-item {:key pos}
   [:div.drop-zone {:class (utils/cs (when highlight-drop-zones? :highlight-all)
                                  (when (get-in @s [:drag-enter pos]) :highlight))

                    :on-drag-over (fn [e]
                                    (.preventDefault e))
                    :on-drag-enter (fn [e]
                                     (.preventDefault e)
                                     (swap! s assoc-in [:drag-enter pos] true))
                    :on-drag-leave (fn []
                                     (swap! s update-in [:drag-enter pos] (fn [] false)))
                    :on-drop (fn [e]
                               (.preventDefault e)
                               (rf/dispatch [:place-card (/ pos 2)])
                               (swap! s update-in [:drag-enter pos] (fn [] false)))}
    [:div.ribbon
     {:class (when highlight-drop-zones? :hide)}]]])

(defn view []
  (let [cards (rf/subscribe [:timeline/cards])
        last-added-id (rf/subscribe [:timeline/last-added])
        animation (rf/subscribe [:move-animation])
        highlight-drop-zones? (rf/subscribe [:highlight-drop-zones?])
        s (reagent/atom {})]
    (fn []
      (let [items (concat [:drop-zone] (interpose :drop-zone @cards) [:drop-zone])]
        [:div.timeline
         [:div.scrolling-wrapper
          {:ref #(rf/dispatch [:DOM/store-node :timeline %])

           :on-wheel (fn [e]
                       (rf/dispatch [:scroll-timeline (.-deltaY e)]))}
          (doall
           (for [[item pos] (map vector items (range))
                 :let [id (:id item)]]
             (if (= item :drop-zone)
               (drop-zone s pos @highlight-drop-zones?)

               [:div.scroll-item {:key pos
                                  :class (utils/cs (when (= id @last-added-id) @animation))}
                [uic/basic-card-view item true nil {:margin "10px 0 10px 0"}]])))]]
         ;[:p (pr-str @s)]
        ))))