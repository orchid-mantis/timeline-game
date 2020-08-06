(ns timeline-game.history
  (:require [re-frame.core :as rf]
            [timeline-game.common :refer [fmap copy-fields]]
            [re-frame-datatable.core :as dt]))

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
      [:span {:class (if well-played? :well-played :wrong-played)}
       (:title player)])))

(defn history-grid []
  (let [players (rf/subscribe [:players])]
    (fn []
      [dt/datatable
       :history-datatable
       [:history/played-cards]
       (into [] (concat
                 (list {::dt/column-key [:round]
                        ::dt/column-label "Round"})

                 (for [[k v] @players]
                   {::dt/column-key [k]
                    ::dt/column-label (:name v)
                    ::dt/render-fn card-title-formatter})))
       {::dt/table-classes ["ui" "celled" "stripped" "table"]}])))
