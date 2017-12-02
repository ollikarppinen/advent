(ns advent-2017.day-1
  (require [clojure.java.io :as io]))

(def input
  (->> "advent_2017/day1"
      io/resource
      slurp
      char-array
      seq
      (map (comp read-string str))))

(println (apply + input))