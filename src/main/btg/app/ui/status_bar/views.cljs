(ns btg.app.ui.status-bar.views
  (:require
   [re-frame.core :as rf]
   [reagent.core :as reagent]
   [btg.app.ui.status-bar.subs]))

(defn view []
  (let [view-data (rf/subscribe [:status-bar/view-data])]
    (fn []
      [:div.status-bar
       [:div.grid.title-2 {:style {:grid-template-columns "150px 300px"}}
        [:span
         "Round: " (:round @view-data)]
        [:span
         (:msg @view-data)]]])))
