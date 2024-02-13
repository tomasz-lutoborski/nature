(ns vectors.magnitude
  (:require [quil.core :as q]))

(defn setup []
  (q/frame-rate 100)
  (q/color-mode :hsl)
  {})

(defn update-state [state]
  {})

(defn draw-state [state]
  (let [center-x (/ (q/width) 2)
        center-y (/ (q/height) 2)
        mouse-x (q/mouse-x)
        mouse-y (q/mouse-y)
        [x y] [(- mouse-x center-x) (- mouse-y center-y)]
        magnitude (q/sqrt (+ (q/pow x 2) (q/pow y 2)))]
    (q/background 250)
    (q/stroke 90)
    (q/stroke-weight 4)
    (q/line center-x center-y mouse-x mouse-y)
    (q/fill 100)
    (q/rect 10 10 magnitude 20)))
