(ns vectors.subtraction
  (:require [quil.core :as q]))

(defn setup []
  (q/frame-rate 100)
  (q/color-mode :hsl)
  {:subvec [0 0]
   :mouse-x (q/mouse-x)
   :mouse-y (q/mouse-y)
   :center [(/ (q/width) 2) (/ (q/height) 2)]})

(defn update-state [{:keys [mouse-x mouse-y center]}]
  (let [[new-x new-y] [(- mouse-x (first center)) (- mouse-y (second center))]]
    {:subvec [new-x new-y]
     :mouse-x (q/mouse-x)
     :mouse-y (q/mouse-y)
     :center center}))

(defn draw-state [{:keys [subvec mouse-x mouse-y center]}]
  (let [[x y] subvec]
    (q/background 250)
    (q/stroke 90 50)
    (q/stroke-weight 4)
    (q/line 0 0 mouse-x mouse-y)
    (js/console.log x y)
    (q/line 0 0 (first center) (second center))
    (q/translate (first center) (second center))
    (q/stroke 90)
    (q/line 0 0 x y)))
