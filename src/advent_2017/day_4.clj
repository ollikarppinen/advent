(ns advent-2017.day-4
  (require [clojure.java.io :as io]
           [clojure.string :as string]))

(def input
  (->> "advent_2017/day4"
       io/resource
       slurp
       clojure.string/split-lines
       (map #(string/split % #"\s"))))

(defn just-ones? [lst]
  (every? #(= 1 %) lst))
(defn count-valid-passphrases [lst]
  (->>
    lst
    (map (comp
           just-ones?
           vals
           frequencies))
    (filter true?)
    count))

(println (count-valid-passphrases input))
(println (count-valid-passphrases (map #(map sort %) input)))

