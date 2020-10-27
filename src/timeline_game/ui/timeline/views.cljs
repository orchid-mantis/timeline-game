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
         (ui/timeline-ribbon highlight-drop-zones?)])

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
                         :overlap 0.03
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
                                     (rf/dispatch [:user/place-card id pos])))}])))})))

(defn view []
  (let [cards (rf/subscribe [:timeline/cards])
        last-added-id (rf/subscribe [:timeline/last-added])
        scrollable? (rf/subscribe [:timeline/scrollable?])
        allow-action? (rf/subscribe [:allow-action?])
        animation (rf/subscribe [:move-animation])
        highlight-drop-zones? (rf/subscribe [:highlight-drop-zones?])]
    (reagent/create-class
     {:reagent-render
      (fn []
        [:div.timeline
         [:div
          {:class (utils/cs :scrolling-wrapper
                            (when @scrollable? :scrollable))

           :ref #(rf/dispatch [:DOM/store-node :timeline %])

           :on-wheel (fn [e]
                       (rf/dispatch [:user/scroll-timeline (.-deltaY e)]))

           :style {:justify-content (when (not @scrollable?) :center)
                   :touch-action (when (not @allow-action?) :none)}}

          [:div.scroll-item
           [drop-zone 0 @highlight-drop-zones?]]

          (doall
           (for [[card pos] (map vector @cards (range))
                 :let [id (:id card)
                       pos (inc pos)]]
             [:div.scroll-item {:key (str id "-" pos)}
              ^{:key id} [ui/game-card
                          card
                          true
                          {:class (utils/cs (when (= id @last-added-id) @animation))}]

              ^{:key (str "dz-" pos)} [drop-zone pos @highlight-drop-zones?]]))]])

      :component-did-update
      (fn []
        (rf/dispatch [:timeline/update-scrollable]))

      :component-did-mount
      (fn []
        (rf/dispatch [:timeline/update-scrollable]))})))
