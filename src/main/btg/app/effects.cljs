(ns btg.app.effects
  (:require
   [re-frame.core :as rf]
   [btg.app.config :as conf]))

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

(defn random-delay [scroll-duration min-delay max-delay]
  (cond
    (> scroll-duration max-delay) 0

    (and (>= scroll-duration min-delay)
         (<= scroll-duration max-delay)) (rand-int (- max-delay scroll-duration))

    :else (+ min-delay
               (rand-int (- max-delay min-delay)))))

(rf/reg-fx
 :side-scroll
 (fn [[node card-width pos callback]]
   (let [max-dist (- (.-scrollWidth node) (.-clientWidth node))

         current-pos (.-scrollLeft node)

         target-pos (-> (* pos (+ card-width 20))
                        (add-offset 40)
                        (min max-dist))

         diff (- current-pos target-pos)

         options (if (< diff 0)
                   {:direction :right
                    :distance (- diff)}
                   {:direction :left
                    :distance diff})

         direction (:direction options)
         distance (:distance options)
         speed 25
         step 10

         ; 500ms is rounded average diff (estimate vs real durations) based on real scroll durations
         estimated-scroll-duration (if (zero? distance)
                                     0
                                     (+ 500 (* speed (/ distance step))))

         delay (if (conf/auto-scroll-delay-enabled?)
                 (random-delay estimated-scroll-duration (conf/auto-scroll-min-delay) (conf/auto-scroll-max-delay))
                 0)]
    ;;  (js/console.log "delay = " delay)
    ;;  (js/console.log "scroll-duration = " estimated-scroll-duration)
    ;;  (js/console.log "total-wait-time = " (+ delay estimated-scroll-duration))
     (js/setTimeout
      #(side-scroll node direction speed distance step callback)
      delay))))

;; (rf/reg-event-fx
;;  :test-scroll
;;  (fn [{:keys [db]} [_ pos]]
;;    (let [timeline-node (get-in db [:dom-nodes :timeline])]
;;      {:db db
;;       :side-scroll [timeline-node pos #(do)]})))

;; (rf/dispatch [:test-scroll 0])