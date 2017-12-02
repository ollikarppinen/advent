(ns advent-2017.day-1
  (require [clojure.java.io :as io]))

(def input
  (->> "advent_2017/day1"
      io/resource
      slurp
       (map (comp read-string str))))

(defn first-last-equal? [arr]
  (= (first arr) (last arr)))

(defn matching-n-step-sum [n, arr]
  (->> arr
       (partition n 1)
       (filter first-last-equal?)
       (map first)
       (apply +)))

(println (matching-n-step-sum 2 (conj input (last input))))
(println (matching-n-step-sum
           (inc (/ (count input) 2))
           (concat input (take (/ (count input) 2) input))))