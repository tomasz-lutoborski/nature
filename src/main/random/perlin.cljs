(ns random.perlin
  (:require [quil.core :as q])
  (:require [utils.slider :as slider]))

(defn setup []
  (q/frame-rate 100)
  (q/color-mode :hsb)
  {:tick 0})

(defn update-state [state]
  {:tick (+ (:tick state) 0.01)})

#_(defn draw-state [state]
    (q/background 255)
    (let [size 50
          gr (q/create-graphics size size)]
      (q/with-graphics gr
        (q/background 255)
        (q/fill 255 0 0)
        (q/ellipse (/ size 2) (/ size 2) (* size (/ 2 3)) (* size (/ 2 3))))
      (q/image gr 0 0)
      (let [px (q/pixels gr)
            half (/ (* size size) 2)]
        (dotimes [i half] (aset-int px (+ i half) (aget px i))))
      (q/update-pixels gr)
      (q/image gr (+ size 20) 0)))

(defn draw-state [state]
  (q/background 255)
  (let [px (q/pixels)
        w (q/width)
        h (q/height)
        scale 0.01]
    (dotimes [i w]
      (dotimes [j h]
        (let [x (* i scale)
              y (* j scale)
              bright (q/map-range (q/noise x y (:tick state)) 0 1 0 255)]
          (aset-int px (+ (* j w) i) (q/color bright bright bright))))))
  (q/update-pixels))

(comment
  (q/width)
  (q/pixels)
  (q/noise 2 2)
  (q/current-fill)
  (q/frame-rate 60)
  (q/height)
  [(q/mouse-x) (q/mouse-y)])