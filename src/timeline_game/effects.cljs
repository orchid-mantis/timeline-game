(ns timeline-game.effects
  (:require
   [re-frame.core :as rf]))

(rf/reg-fx
 :timeout
 (fn [[time event]]
   (js/setTimeout #(rf/dispatch event) time)))

(defn horizontal-scroll [node delta]
  (when node
    (set! (.-scrollLeft node) (+ (.-scrollLeft node) delta))))

(rf/reg-fx
 :scroll-timeline
 (fn [[node delta]]
   (horizontal-scroll node delta)))

(defn side-scroll [el direction speed distance step callback]
  (let [scroll-amount (atom 0)
        op (case direction
             :left  -
             :right +)
        timer (atom nil)]
    (reset! timer (js/setInterval (fn [] (let [scroll-left (.-scrollLeft el)
                                               next-scroll-amount (+ @scroll-amount step)
                                               diff (if (> next-scroll-amount distance)
                                                      (- next-scroll-amount distance)
                                                      0)]
                                           (swap! scroll-amount #(+ % step))
                                           (set! (.-scrollLeft el) (op scroll-left (- step diff)))
                                           (when (>= @scroll-amount distance)
                                             (js/clearInterval @timer)
                                             (callback))))
                                  speed))))

(defn add-offset [position offset]
  (if (pos? position)
    (+ position offset)
    position))

(rf/reg-fx
 :side-scroll
 (fn [[node pos callback]]
   (let [max-dist (- (.-scrollWidth node) (.-clientWidth node))

         current-pos (.-scrollLeft node)

         target-pos (-> (* pos (+ 166 20))
                        (add-offset 40)
                        (min max-dist))

         diff (- current-pos target-pos)

         options (if (< diff 0)
                   {:direction :right
                    :distance (- diff)}
                   {:direction :left
                    :distance diff})]
     (side-scroll node (:direction options) 25 (:distance options) 10 callback))))
