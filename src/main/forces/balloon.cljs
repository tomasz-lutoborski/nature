(ns forces.balloon (:require [quil.core :as q]
                             [util.vector :as uv]))

(def ball-radius 20)

(defn setup []
  (q/frame-rate 50)
  (q/color-mode :hsl)
  {:pos {:x 20 :y 570}
   :vel {:x 0 :y -0.5}
   :noise-step 0})

(defn update-vel [{pos-x :x pos-y :y} {vel-x :x vel-y :y}  width height]
  (let [hit-left-or-right? (or (<= (- pos-x ball-radius) 0) (>= (+ pos-x ball-radius) width))
        hit-top-or-bottom? (or (<= (- pos-y ball-radius) 0) (>= (+ pos-y ball-radius) height))]
    {:x (if hit-left-or-right? (- vel-x) vel-x)
     :y (if hit-top-or-bottom? (- vel-y) vel-y)}))

(defn update-state [{:keys [pos vel noise-step]}]
  (let [width (q/width)
        height (q/height)
        new-vel (update-vel pos vel width height)
        acc {:x (- (q/noise noise-step) 0.5) :y 0}]
    {:pos (uv/add-vec pos new-vel)
     :vel (uv/add-vec new-vel acc)
     :noise-step (+ noise-step 0.001)}))

(defn draw-state [{:keys [pos]}]
  (q/background 120 100 100)
  (q/fill 220 100 100)
  (q/ellipse (:x pos) (:y pos) (* 2 ball-radius) (* 2 ball-radius)))

(comment
  (q/with-sketch (q/get-sketch-by-id "app")
    (q/no-loop))
  (q/with-sketch (q/get-sketch-by-id "app")
    (q/start-loop))
  (q/with-sketch (q/get-sketch-by-id "app")
    (- (q/noise 0.2) 0.5))
  (q/with-sketch (q/get-sketch-by-id "app")
    (swap! (q/state-atom) assoc :pos {:x 300 :y 570}))
  (q/with-sketch (q/get-sketch-by-id "app")
    (swap! (q/state-atom) assoc :vel {:x 0 :y -0.50}))
  (q/with-sketch (q/get-sketch-by-id "app")
    (uv/add-vec helium-force {:x (q/noise 0.123) :y 1}))
  (uv/add-vec {:x 0 :y 0} {:x 1 :y 1}))
