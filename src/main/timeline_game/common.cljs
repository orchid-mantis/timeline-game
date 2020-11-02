(ns timeline-game.common
  (:require [re-frame.core :as rf]))

;; -- Subscriptions -----------------------------------------------------------

(rf/reg-sub
 :DOM/get-node
 (fn [db [_ key]]
   (get-in db [:dom-nodes key])))

;; -- Events ------------------------------------------------------------------

(rf/reg-event-db
 :DOM/store-node
 (fn [db [_ key node]]
   (assoc-in db [:dom-nodes key] node)))

;; -- Functions ------------------------------------------------------------------

(defn put-before [items pos item]
  (let [items (remove #{item} items)
        head (take pos items)
        tail (drop pos items)]
    (vec (concat head [item] tail))))

(defn remove-card [ids id]
  (remove #(= % id) ids))

(defn ordered? [ids cards]
  (let [years (map #(:year (cards %)) ids)]
    (or (empty? ids) (apply <= years))))

(defn map-v [f map]
  (into {} (for [[k v] map]
             [k (f v)])))

(defn map-kv [f map]
  (into {} (for [[k v] map]
             [k (f k v)])))

(defn copy-fields [obj-map obj-key field-keys mod-map]
  (map-v (fn [m]
              (let [obj (obj-map (obj-key m))]
                (reduce (fn [m k] (assoc m k (k obj))) m field-keys)))
            mod-map))
