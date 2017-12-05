(ns advent-2017.day-5
  (require [clojure.java.io :as io]
           [clojure.string :as string]))

(def input
  (->> "advent_2017/day5"
       io/resource
       slurp
       string/split-lines
       (mapv read-string)))
(defn jump-around [updater]
  (loop [i 0 arr input steps 0]
    (if (or (< i 0) (>= i (count input)))
      (println steps)
      (recur
        (+ i (get arr i))
        (updater arr i)
        (inc steps)))))

(jump-around #(update-in %1 [%2] inc))
(jump-around #(update-in %1 [%2] (if (> (get %1 %2) 2) dec inc)))
