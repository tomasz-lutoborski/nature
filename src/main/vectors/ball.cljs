(ns vectors.ball
  (:require [quil.core :as q])
  (:require [utils.slider :as slider]))

(def ball-radius 20)

(defn setup []
  (q/frame-rate 100)
  (q/color-mode :hsb)
  {:pos [20 20]
   :vel [2 3]})


(defn in-bounds? [pos]
  (let [top ball-radius
        down (- (q/height) ball-radius)
        left ball-radius
        right (- (q/width) ball-radius)]
    [(<= left (first pos) right)
     (<= top (second pos) down)]))

(defn update-pos [pos vel]
  (let [new-pos (map + pos vel)
        [x-in-bounds y-in-bounds] (in-bounds? new-pos)
        [x-vel y-vel] vel
        new-vel (cond (and (not x-in-bounds) (not y-in-bounds)) [(- x-vel) (- y-vel)]
                      (not x-in-bounds) [(- x-vel) y-vel]
                      (not y-in-bounds) [x-vel (- y-vel)]
                      :else vel)]
    (if (and x-in-bounds y-in-bounds) [new-pos new-vel] [pos new-vel])))

(defn update-state [state]
  (let [[new-pos new-vel] (update-pos (:pos state) (:vel state))]
    {:pos new-pos
     :vel new-vel}))

(defn draw-state [state]
  (q/background 255)
  (q/fill 0 0 0)
  (q/ellipse (first (:pos state)) (second (:pos state)) (* 2 ball-radius) (* 2 ball-radius)))


(comment
  (in-bounds? [1000 1000])
  (update-pos [1000 1000] [1 1])
  (q/width)
  (q/pixels)
  (q/noise 2 2)
  (q/current-fill)
  (q/frame-rate 60)
  (q/height)
  [(q/mouse-x) (q/mouse-y)])