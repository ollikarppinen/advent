(ns advent-2017.day-6
  (require [clojure.java.io :as io]
           [clojure.string :as string]))

(def input
  (mapv read-string
       (string/split
         (->> "advent_2017/day6"
              io/resource
              slurp)
         #"\s")))

(defn index-of-first-lowest [xs]
  (reduce
    #(if (< (nth xs %1) (nth xs %2)) %2 %1)
    (range (count xs))))

(defn reallocate [i xs]
  (let [n (count xs)
        reallocation (apply mapv + (partition n n (repeat 0)
                                              (concat
                                                (repeat (inc i) 0)
                                                (repeat (nth xs i) 1))))]
    (apply mapv + (vector (assoc xs i 0) reallocation))))

(loop [perms {} state input i 0]
  (if (contains? perms state)
    (println i (- i (get perms state)))
    (recur (conj perms {state i})
           (reallocate (index-of-first-lowest state) state)
           (inc i))))
