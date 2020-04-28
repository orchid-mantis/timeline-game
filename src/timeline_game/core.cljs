(ns timeline-game.core
  (:require [re-frame.core :as rf]
            [reagent.core :as reagent]
            [reagent.dom :as reagent-dom]))

(defn root-view
  "Render the page"
  []
  [:h1 "Timeline Game"])

(defn ^:export render []
  (reagent-dom/render [root-view]
                      (js/document.getElementById "app")))

(defn ^:export init []
  (render))

