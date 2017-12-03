(ns advent-2017.day-3
  (require [clojure.test :as test]))

(def input 347991)

(defn closest-odd-sqr-root [x]
  (loop [cnt 1]
    (if (>= (* cnt cnt) x)
      cnt
      (recur (+ cnt 2)))))

(test/are [x y] (= x (closest-odd-sqr-root y))
                1 1
                3 2
                3 9
                5 10
                5 25
                7 26)

(defn abs [x] (if (neg? x) (* -1 x) x))

(defn orthogonal-points [x]
  (let [corner (* x x)
        step (/ (dec x) 2)]
    (map #(- corner (* step %)) '(1 3 5 7))))

(test/are [x y] (= x (orthogonal-points y))
                '(1 1 1 1) 1
                '(8 6 4 2) 3
                '(23 19 15 11) 5)

(defn sideways-dist [x]
  (->> x
       closest-odd-sqr-root
       orthogonal-points
       (map (comp abs #(- x %)))
       (reduce min)))

(test/are [x y] (= x (sideways-dist y))
                0 1
                1 9
                2 13)

(defn manhattan-distance [x]
  (+ (/ (dec (closest-odd-sqr-root x)) 2)
     (sideways-dist x)))

(println (manhattan-distance input))

(defn up [location]
  (let [[x y] location]
    [x (inc y)]))
(defn down [location]
  (let [[x y] location]
    [x (dec y)]))
(defn left [location]
  (let [[x y] location]
    [(dec x) y]))
(defn right [location]
  (let [[x y] location]
    [(inc x) y]))

(def direction {{:up false :down false :right false :left false} right
                {:up false :down false :right false :left true } up
                {:up false :down true  :right false :left false} left
                {:up false :down true  :right true  :left false} left
                {:up false :down false :right true  :left false} down
                {:up true  :down false :right true  :left false} down
                {:up true  :down false :right false :left false} right
                {:up true  :down false :right false :left true } right
                {:up false :down true  :right false :left true } up})

(defn next-location [spiral, location]
  (let [move (get direction
                  {:up (contains? spiral (up location))
                   :down (contains? spiral (down location))
                   :left (contains? spiral (left location))
                   :right (contains? spiral (right location))
                   })]
    (move location)))

(def adj [[1, 1], [0, 1], [1, 0], [-1, 0], [-1, -1], [0, -1], [1, -1], [-1, 1]])
(defn adj-sum [spiral, location]
  (->> adj
       (mapv #(mapv + location %))
       (mapv #(get-in spiral [%, :sum] 0))
       (apply +)))

(defn update-spiral [spiral, location, i]
  (let [k (next-location spiral location)
        v {:i (inc i) :key k :sum (adj-sum spiral k)}]
    (conj spiral {k v})))

(loop [i 1 spiral { [0 0] { :key [0 0] :i 1 :sum 1 }} location [0 0]]
  (let [sum (get-in spiral [location :sum])]
    (if (> sum input)
      (println sum)
      (recur
        (inc i)
        (update-spiral spiral location i)
        (next-location spiral location)))))
