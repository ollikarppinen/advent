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

(println "Task 1:" (manhattan-distance input))

(def adj [[1, 1], [0, 1], [1, 0], [-1, 0], [-1, -1], [0, -1], [1, -1], [-1, 1]])
(defn adj-sum [spiral, location]
  (->> adj
       (mapv #(mapv + location %))
       (mapv #(get-in spiral [%, :sum] 0))
       (apply +)))

(defn update-spiral [spiral, location, i]
  (conj spiral
        {location
         {:i (inc i) :key location :sum (adj-sum spiral location)}}))

(defn update-location [direction location]
  (let [[x y] location]
    (cond
      (= direction :left)  [(inc x) y]
      (= direction :up)    [x (inc y)]
      (= direction :right) [(dec x) y]
      (= direction :down)  [x (dec y)])))

(def next-direction {:left :up :up :right :right :down :down :left})
(defn update-direction [spiral direction location]
  (let [next-dir (get next-direction direction)
        next-dir-location (update-location next-dir location)]
    (if (contains? spiral next-dir-location) direction next-dir)))

(def spiral-init { [0 0] { :key [0 0] :i 1 :sum 1 }})

(loop [i 1 spiral spiral-init location [0 0] direction :left]
  (let [sum (get-in spiral [location :sum])
        next-location (update-location direction location)]
    (if (> sum input)
      (println "Task 2:" sum)
      (recur
        (inc i)
        (update-spiral spiral next-location i)
        next-location
        (update-direction spiral direction next-location)))))
