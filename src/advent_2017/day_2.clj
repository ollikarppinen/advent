(ns advent-2017.day-2
  (require [clojure.java.io :as io]))

(def input
  (->> "advent_2017/day2"
       io/resource
       slurp
       clojure.string/split-lines
       (map (comp (partial map read-string)
            #(clojure.string/split % #"\s")))))

(defn min-max-diff [arr]
  (- (apply max arr)
     (apply min arr)))

(def res
  (->> input
       (map min-max-diff)
       (apply +)))

(println res)

(defn all-pairs [arr]
  (clojure.math.combinatorics/combinations arr 2))

(defn even-division [arr]
  (let [a (apply max arr)
        b (apply min arr)]
    (if (zero? (mod a b))
      (/ a b)
      0)))

(defn even-div-sum [arr]
  (->> arr
       all-pairs
       (map even-division)
       (apply +)))

(println (->> input
              (map even-div-sum)
              (apply +)))
