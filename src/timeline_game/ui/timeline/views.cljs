(ns timeline-game.ui.timeline.views
  (:require
   [re-frame.core :as rf]
   [reagent.core :as reagent]
   [reagent.dom :as reagent-dom]
   [timeline-game.ui.timeline.events]
   [timeline-game.ui.timeline.subs]
   [timeline-game.ui.components :as ui]
   [timeline-game.ui.utils :as utils]))

(defn drop-zone [pos highlight-drop-zones?]
  (let [allow-drop? (rf/subscribe [:allow-drop?])]
    (reagent/create-class
     {:reagent-render
      (fn [_ highlight-drop-zones?]
        [:div.drop-zone {:class (when highlight-drop-zones? :highlight-all)}
         [:div.ribbon
          {:class (when highlight-drop-zones? :hide)}]])

      :component-did-update
      (fn [this]
        (let [node (reagent-dom/dom-node this)]
          (rf/dispatch [:dnd/dropzone
                        node
                        {:checker (fn [_ _ dropped?] (and dropped? @allow-drop?))}])))

      :component-did-mount
      (fn [this]
        (let [node (reagent-dom/dom-node this)]
          (rf/dispatch [:dnd/dropzone
                        node
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

                         :ondrop (fn [e]
                                   (let [draggableElement (.-relatedTarget e)
                                         str-id (.. draggableElement -dataset -id)
                                         id (js/parseInt str-id)]
                                     (rf/dispatch [:user/place-card id (/ pos 2)])))}])))})))

(defn view []
  (let [cards (rf/subscribe [:timeline/cards])
        last-added-id (rf/subscribe [:timeline/last-added])
        scrollable? (rf/subscribe [:timeline/scrollable?])
        animation (rf/subscribe [:move-animation])
        highlight-drop-zones? (rf/subscribe [:highlight-drop-zones?])]
    (reagent/create-class
     {:reagent-render
      (fn []
        (let [items (concat [:drop-zone] (interpose :drop-zone @cards) [:drop-zone])]
          [:div.timeline
           [:div
            {:class (utils/cs :scrolling-wrapper
                              (when @scrollable? :scrollable))

             :ref #(rf/dispatch [:DOM/store-node :timeline %])

             :on-wheel (fn [e]
                         (rf/dispatch [:user/scroll-timeline (.-deltaY e)]))

             :style {:display :flex
                     :justify-content (when (not @scrollable?) :center)}}
            (doall
             (for [[item pos] (map vector items (range))
                   :let [id (:id item)]]
               [:div.scroll-item {:key pos}
                (if (= item :drop-zone)
                  [drop-zone pos @highlight-drop-zones?]
                  [ui/basic-card-view
                   item
                   true
                   (utils/cs (when (= id @last-added-id) @animation))
                   {:margin "10px 0 10px 0"}])]))]]))

      :component-did-update
      (fn []
        (rf/dispatch [:timeline/update-scrollable]))

      :component-did-mount
      (fn []
        (rf/dispatch [:timeline/update-scrollable]))})))
