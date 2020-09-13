(ns timeline-game.ui.timeline.views
  (:require
   [re-frame.core :as rf]
   [reagent.core :as reagent]
   [reagent.dom :as reagent-dom]
   ["interactjs" :as interact]
   [timeline-game.ui.timeline.events]
   [timeline-game.ui.timeline.subs]
   [timeline-game.ui.components :as uic]
   [timeline-game.ui.utils :as utils]))

(defn drop-zone [s pos highlight-drop-zones?]
  (reagent/create-class
   {:reagent-render
    (fn [s pos highlight-drop-zones?]
      [:div.drop-zone {:class (when highlight-drop-zones? :highlight-all)}
       [:div.ribbon
        {:class (when highlight-drop-zones? :hide)}]])

    :component-did-mount
    (fn [this]
      (.dropzone (interact (reagent-dom/dom-node this))
                 #js
                  {:accept ".draggable"
                   :overlap 0.04
                   :ondragenter (fn [e]
                                  (let [draggableElement (.-relatedTarget e)
                                        dropzoneElement (.-target e)]
                                    (.add (.-classList dropzoneElement) "highlight")
                                    (.add (.-classList draggableElement) "can-drop")))

                   :ondragleave (fn [e]
                                  (.remove (.. e -target -classList) "highlight")
                                  (.remove (.. e -relatedTarget -classList) "can-drop"))

                   :ondrop #(rf/dispatch [:place-card (/ pos 2)])}))}))

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
             [:div.scroll-item {:key pos}
              (if (= item :drop-zone)
                [drop-zone s pos @highlight-drop-zones?]
                [uic/basic-card-view
                 item
                 true
                 (utils/cs (when (= id @last-added-id) @animation))
                 {:margin "10px 0 10px 0"}])]))]]
         ;[:p (pr-str @s)]
        ))))