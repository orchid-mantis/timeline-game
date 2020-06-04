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
