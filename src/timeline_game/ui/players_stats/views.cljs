(ns timeline-game.ui.players-stats.views
  (:require
   [re-frame.core :as rf]
   [timeline-game.ui.players-stats.subs]
   [timeline-game.ui.components.icons :as icon]))

(defn players-stats [player]
  (let [stats (rf/subscribe [:player/stats-view player])
        hand-size (rf/subscribe [:hand/size player])]
    (fn []
      (let [last-played-card (:last-played-card @stats)
            last-valid-move? (:last-valid-move? @stats)]
        [:div.row-data
         [:div {:title "Well-played count"}
          [icon/well-played]
          [:span (:well-played-count @stats)]]

         [:div {:title "Wrong-played count"}
          [icon/wrong-played]
          [:span (:wrong-played-count @stats)]]

         [:div {:title "Number of cards in hand"}
          [icon/card-deck]
          [:span @hand-size]]

         [:div.section {:title (case last-valid-move?
                                 true "Well-played"
                                 false "Wrong-played"
                                 "Last played card")}
          (case last-valid-move?
            true  [icon/well-played]
            false [icon/wrong-played]
            [icon/caret-right])]

         [:div {:title "Last played card"}
          [:span
           (get last-played-card :title "-----")]]]))))

(defn player-name [player curr-player]
  (let [highlight? (= (:id player) curr-player)]
    [:div.grid {:style {:grid-template-columns "15px auto"}
                :title (when highlight? "Current player")}
     [:div.center
      (when highlight?
        [icon/caret-right])]

     [:span {:style {:font-weight (when highlight? :bold)}}
      (:name player)]]))

(defn view []
  (let [players (rf/subscribe [:players])
        curr-player (rf/subscribe [:game/current-player])]
    (fn []
      (let [items (interleave (vals @players) (keys @players))]
        [:div.players-stats
         (doall
          (for [[p index] (map vector items (range))]
            [:div.column {:key index}
             (if (keyword? p)
               [players-stats p]
               [player-name p @curr-player])]))]))))
