(ns util.vector
  (:require [quil.core :as q]))

(defn add-vec [{x1 :x y1 :y} {x2 :x y2 :y}]
  {:x (+ x1 x2) :y (+ y1 y2)})

(defn sub-vec [{x1 :x y1 :y} {x2 :x y2 :y}]
  {:x (- x1 x2) :y (- y1 y2)})

(defn norm-vec [{x :x y :y}]
  (let [mag (q/sqrt (+ (q/pow x 2) (q/pow y 2)))]
    {:x (/ x mag) :y (/ y mag)}))

(defn mult-vec [{x :x y :y} scale]
  {:x (* scale x) :y (* scale y)})

(defn limit-vec [{:keys [x y] :as vec} limit]
  (if (> (+ (q/pow x 2) (q/pow y 2)) (q/pow limit 2))
    (mult-vec (norm-vec vec) limit) vec))

(defn div-vec [{x :x y :y} scale]
  {:x (/ x scale) :y (/ y scale)})

(defn distance [{x1 :x y1 :y} {x2 :x y2 :y}]
  (q/sqrt (+ (q/pow (- x2 x1) 2) (q/pow (- y2 y1) 2))))

(defn direction [{x1 :x y1 :y} {x2 :x y2 :y}]
  (norm-vec {:x (- x1 x2) :y (- y1 y2)}))