(ns random.normal
  (:require [quil.core :as q])
  (:require [utils.slider :as slider]))

(defn setup []
  (q/frame-rate 100)
  (q/color-mode :hsb)
  {:rand-x 250
   :rand-y 250
   :hue 0
   :std-dev 10})

(defn increase-hue [hue]
  (mod (+ hue 1) 360))

(defn adjust-random-gaussian [sigma mu]
  (+ mu (* sigma (q/random-gaussian))))

;; random walk with slight bias towards walking towards the mouse
(defn update-state [{:keys [hue std-dev] :as state}]
  {:rand-x (adjust-random-gaussian std-dev 250)
   :rand-y (adjust-random-gaussian std-dev 250)
   :hue (increase-hue hue)
   :std-dev (if (q/key-pressed?) (+ 1 std-dev) (if (> std-dev 10) (- std-dev 1) 10))})

(defn draw-state [{:keys [rand-x rand-y hue]}]
  (q/no-stroke)
  (q/fill hue 100 100)
  (q/ellipse rand-x rand-y 10 10))

(comment
  (q/width)
  (q/current-fill)
  (q/frame-rate 60)
  (q/height)
  [(q/mouse-x) (q/mouse-y)])