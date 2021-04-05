(ns btg.app.ui.status-bar.subs
  (:require
   [re-frame.core :as rf]))

(rf/reg-sub
 :status-bar/view-data
 (fn []
   [(rf/subscribe [:game/round])
    (rf/subscribe [:game/mode])
    (rf/subscribe [:game/players-turn?])
    (rf/subscribe [:game/result])])
 (fn [[round mode players-turn? game-result]]
   (let [msg (if (= game-result :await)
               (if players-turn?
                 "It's your turn!"
                 "Opponent's turn")
               (case game-result
                 :player-won "Game over: You won!"
                 :player-lost "Game over: You lost"
                 nil))]
     {:round round
      :mode mode
      :msg msg})))
