(ns btg.app.ui.card-provider
  (:require
   [re-frame.core :as rf]
   [btg.app.ui.game-cards.basic-card :as basic-card]
   [btg.app.ui.game-cards.image-card :as image-card]))

(def game-card {:basic-card {:comp basic-card/view
                             :theme basic-card/theme
                             :width basic-card/card-width}

                :image-card {:comp image-card/view
                             :theme image-card/theme
                             :width image-card/card-width}})

(rf/reg-event-db
 :game-card/init
 (fn [db [_ key]]
   (let [card (get game-card key)]
     (-> db
         (assoc-in [:app :game-card] (select-keys card [:comp :width]))
         (assoc-in [:app :theme] (:theme card))))))

(rf/reg-sub
 :game-card/comp
 (fn [db _]
   (get-in db [:app :game-card :comp])))

; (rf/dispatch [:game-card/init :image-card])