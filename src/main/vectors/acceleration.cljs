(ns vectors.acceleration
  (:require [quil.core :as q]))

(defn setup []
  (q/frame-rate 30)
  (q/color-mode :hsl)
  {:pos {:x 24 :y 24}
   :vel {:x 2 :y 1}
   :acc {:x 0.05 :y 0.05}})

(defn add-vec [{x1 :x y1 :y} {x2 :x y2 :y}]
  {:x (+ x1 x2) :y (+ y1 y2)})

(defn update-vel [{pos-x :x pos-y :y} {vel-x :x vel-y :y} {acc-x :x acc-y :y} width height]
  (let [hit-left-or-right? (or (<= (- pos-x 20) 0) (>= (+ pos-x 20) width))
        hit-top-or-bottom? (or (<= (- pos-y 20) 0) (>= (+ pos-y 20) height))]
    [{:x (if hit-left-or-right? (- vel-x) vel-x)
      :y (if hit-top-or-bottom? (- vel-y) vel-y)}
     {:x (if hit-left-or-right? (- acc-x) acc-x)
      :y (if hit-top-or-bottom? (- acc-y) acc-y)}]))

(defn update-state [{:keys [pos vel acc]}]
  (let [width (q/width)
        height (q/height)
        [new-vel new-acc] (update-vel pos vel acc width height)]
    {:pos (add-vec pos new-vel)
     :vel (add-vec new-vel new-acc)
     :acc new-acc}))

(defn draw-state [{:keys [pos]}]
  (q/clear)
  (q/background 140 80 100)
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