(ns advent-2017.day-1
  (require [clojure.java.io :as io]))

(def input
  (->> "advent_2017/day1"
      io/resource
      slurp
      char-array
      seq
      (map (comp read-string str))))

(def circular (conj input (last input)))
(def window (partition 2 1 circular))
(def matching (filter #(apply = %) window))
(def sum (apply + (map first matching)))
(println (str "--- Day 1: Inverse Captcha ---\n" sum))