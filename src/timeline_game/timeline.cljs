(ns timeline-game.timeline
  (:require [re-frame.core :as rf]
            [reagent.core :as reagent]
            [timeline-game.common :refer [put-before remove-card]]
            [timeline-game.basic-card :as basic-card]
            [timeline-game.ui-helpers :as ui]))

;; -- Subscriptions -----------------------------------------------------------

(rf/reg-sub
 :timeline-ids
 (fn [db]
   (get-in db [:timeline :ids])))

(rf/reg-sub
 :timeline/last-added
 (fn [db]
   (get-in db [:timeline :last-added-id])))

(rf/reg-sub
 :timeline/cards
 (fn []
   [(rf/subscribe [:cards])
    (rf/subscribe [:timeline-ids])])
 (fn [[cards timeline]]
   (vec (map cards timeline))))

(rf/reg-sub
 :move-animation
 (fn []
   (rf/subscribe [:turn]))
 (fn [turn]
   (case turn
     :well-placed-card :flip-in-hor-top
     :misplaced-card :rotate-out-2-cw
     nil)))

(rf/reg-sub
 :player/selected-card-id
 (fn [db]
   (get-in db [:player :selected-card-id])))

(rf/reg-sub
 :highlight-drop-zones?
 (fn []
   (rf/subscribe [:player/selected-card-id]))
 (fn [selected-card]
   (not= selected-card :nothing)))

;; -- Events ------------------------------------------------------------------

(rf/reg-event-fx
 :place-card
 (fn [{:keys [db]} [_ pos]]
   (let [id (get-in db [:player :selected-card-id])]
     {:db (-> db
              (update-in [:player :hand :ids] remove-card id)
              (update-in [:timeline :ids] put-before pos id)
              (assoc-in [:player :selected-card-id] :nothing))
      :dispatch [:eval-move :player id]})))

(rf/reg-event-fx
 :scroll-timeline
 (fn [{:keys [db]} [_ delta]]
   (let [node (get-in db [:dom-nodes :timeline])
         turn (get-in db [:game :turn])
         player (get-in db [:game :player])
         game-result (get-in db [:game :result])
         players-turn? (and (= player :player) (= turn :ready))
         game-ended? (not= game-result :await)
         allow-scroll? (or game-ended? players-turn? )]
     (merge {:db db}
            (when allow-scroll?
              {:apply-scroll-timeline [node delta]})))))

(defn horizontal-scroll [node delta]
  (when node
    (set! (.-scrollLeft node) (+ (.-scrollLeft node) delta))))

(rf/reg-fx
 :apply-scroll-timeline
 (fn [[node delta]]
   (horizontal-scroll node delta)))

(defn drop-zone [s pos highlight-drop-zones?]
  [:div.scroll-item {:key pos}
   [:div.drop-zone {:class (ui/cs (when highlight-drop-zones? :highlight-all)
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

;; -- UI ------------------------------------------------------------------

(defn view []
  (let [cards (rf/subscribe [:timeline/cards])
        last-added-id (rf/subscribe [:timeline/last-added])
        animation (rf/subscribe [:move-animation])
        highlight-drop-zones? (rf/subscribe [:highlight-drop-zones?])
        s (reagent/atom {})]
    (fn []
      (let [items (concat [:drop-zone] (interpose :drop-zone @cards) [:drop-zone])]
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
                                 :class (ui/cs (when (= id @last-added-id) @animation))}
               [basic-card/view item true nil {:margin "10px 0 10px 0"}]])))]
         ;[:p (pr-str @s)]
        ))))
