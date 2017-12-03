(ns advent-2017.day-3
  (require [clojure.test :as test]))

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

(test/are [x y] (= x (manhattan-distance y))
                0 1
                3 12
                2 23
                31 1024)

(println (manhattan-distance 347991))