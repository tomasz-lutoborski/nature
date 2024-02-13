(ns vectors.mouse-direction
  (:require [quil.core :as q]))

(def topspeed 5)

(defn setup []
  (q/frame-rate 30)
  (q/color-mode :hsl)
  {:pos {:x 24 :y 24}
   :vel {:x 2 :y 1}})

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

(defn update-acc [pos-vec]
  (let [mouse-vec {:x (q/mouse-x) :y (q/mouse-y)}
        dir-vec (sub-vec mouse-vec pos-vec)
        norm-dir-vec (norm-vec dir-vec)]
    (mult-vec norm-dir-vec 0.2)))

(defn update-state [{:keys [pos vel]}]
  (let [acc (update-acc pos)]
    {:pos (add-vec pos vel)
     :vel (limit-vec (add-vec vel acc) topspeed)
     :acc acc}))

(defn draw-state [{:keys [pos]}]
  (q/clear)
  (q/background 130 80 100)
  (q/fill 300 100 100)
  (q/no-stroke)
  (q/ellipse (pos :x) (pos :y) 40 40))

(comment
  (add-vec {:x 1 :y 1} {:x 10 :y 10})
  (q/with-sketch (q/get-sketch-by-id "app")
    (update-state {:pos {:x 10 :y 10} :vel {:x 10 :y 10} :acc {:x 0.1 :y 0.1}}))
  (q/with-sketch (q/get-sketch-by-id "app")
    (q/no-loop))
  (q/with-sketch (q/get-sketch-by-id "app")
    (q/start-loop))
  (q/with-sketch (q/get-sketch-by-id "app")
    (q/state))
  (q/with-sketch (q/get-sketch-by-id "app")
    (swap! (q/state-atom) assoc :pos {:x 10 :y 10}))
  (q/with-sketch (q/get-sketch-by-id "app")
    (swap! (q/state-atom) assoc :vel {:x 1 :y 1})))