(ns timeline-game.history
  (:require [re-frame.core :as rf]
            [timeline-game.common :refer [fmap copy-fields]]
            [re-frame-datatable.core :as dt]
            [re-frame-datatable.views :as dt-views]))

(rf/reg-sub
 :players
 (fn [db]
   (get-in db [:players])))

(rf/reg-sub
 :history
 (fn [db]
   (get-in db [:history])))

(rf/reg-sub
 :history/played-cards
 (fn []
   [(rf/subscribe [:cards])
    (rf/subscribe [:history])])
 (fn [[cards history]]
   (let [played-cards (fmap (partial copy-fields cards :id [:title]) history)]
     (for [[k v] played-cards]
       (assoc v :round k)))))

(defn- card-title-formatter [player]
  (let [well-played? (:valid? player)]
    (if (nil? well-played?)
      [:span "n/a"]
      [:div.history-grid-row
       [:i.fas {:class (if well-played? :well-played :wrong-played)}]
       [:span (:title player)]])))

(defn empty-tbody-formatter []
  [:em
   "No cards were played yet"])

(defn history-grid []
  (let [players (rf/subscribe [:players])]
    (fn []
      [:div
       [dt-views/default-pagination-controls :history-datatable [:history/played-cards]]
       [dt/datatable
        :history-datatable
        [:history/played-cards]
        (into [] (concat
                  (list {::dt/column-key [:round]
                         ::dt/column-label "Round"
                         ::dt/sorting {::dt/enabled? true}})

                  (for [[k v] @players]
                    {::dt/column-key [k]
                     ::dt/column-label (:name v)
                     ::dt/render-fn card-title-formatter})))
        {::dt/table-classes ["ui" "table"]
         ::dt/empty-tbody-component empty-tbody-formatter
         ::dt/pagination {::dt/enabled? true, ::dt/per-page 10}}]])))
