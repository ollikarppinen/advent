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