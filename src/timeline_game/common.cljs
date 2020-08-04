(ns timeline-game.common)

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

(defn fmap [f map]
  (into {} (for [[k v] map]
             [k (f v)])))

(defn copy-fields [obj-map obj-key field-keys mod-map]
  (fmap (fn [m]
          (let [obj (obj-map (obj-key m))]
            (reduce (fn [m k] (assoc m k (k obj))) m field-keys)))
        mod-map))
