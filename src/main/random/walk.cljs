(ns random.walk
  (:require [quil.core :as q]
            [utils.slider :as slider]))

(defn setup []
  (q/frame-rate 30)
  (q/color-mode :rgb)
  {:x 250
   :y 250
   :size 10
   :t 0})

;; random walk with slight bias towards walking to the left
#_(defn update-state [{:keys [x y size] :as state}]
    (let [direction (rand 100)
          [dx dy] (cond
                    (< direction 10)    [0 (- size)]
                    (< direction 20)  [size 0]
                    (< direction 30) [0 size]
                    :else   [(- size) 0])]
      (-> state
          (assoc :x (+ x dx))
          (assoc :y (+ y dy)))))

;; random walk without any bias
#_(defn update-state [{:keys [x y size] :as state}]
    (let [direction (rand-nth [:top :right :bottom :left])
          [dx dy] (case direction
                    :top    [0 (- size)]
                    :right  [size 0]
                    :bottom [0 size]
                    :left   [(- size) 0])]
      (-> state
          (assoc :x (+ x dx))
          (assoc :y (+ y dy)))))

;; random walk with slight bias towards walking towards the mouse
#_(defn update-state [{:keys [x y size] :as state}]
    (letfn [(follow-mouse [] (let [mouse-x (q/mouse-x)
                                   mouse-y (q/mouse-y)]
                               (cond
                                 (< mouse-x x) [(- size) 0]
                                 (> mouse-x x) [size 0]
                                 (< mouse-y y) [0 (- size)]
                                 (> mouse-y y) [0 size]
                                 :else [0 0])))]
      (let [direction (rand-nth [:top :right :bottom :left])
            [dx dy] (case direction
                      :top    [0 (- size)]
                      :right  [size 0]
                      :bottom [0 size]
                      :left   [(- size) 0])
            follow-mouse? (rand-nth [true false])]
        (-> state
            (assoc :x (+ x (if follow-mouse? (first (follow-mouse)) dx)))
            (assoc :y (+ y (if follow-mouse? (second (follow-mouse)) dy)))))))

#_(defn accept-reject []
    (let [x (int (Math/floor (q/random 10)))
          y (int (Math/floor (q/random 10)))]
      (if (< y x)
        (if (< 0.5 (q/random 1)) x (- x))
        (recur))))

;; random walk generated accept-reject method
#_(defn update-state [{:keys [x y size] :as state}]
    (let [dx (accept-reject)
          dy (accept-reject)]
      (-> state
          (assoc :x (+ x dx))
          (assoc :y (+ y dy)))))

;; random walk with perlin noise
(defn update-state [{:keys [x y size t] :as state}]
  (let [dx (q/map-range (q/noise (q/random t)) 0 1 (- size) size)
        dy (q/map-range (q/noise (q/random t)) 0 1 (- size) size)]
    (-> state
        (assoc :x (+ x dx))
        (assoc :y (+ y dy))
        (update :t inc))))

(defn draw-state [state]
  (q/fill 205 100 50)
  #_(q/background 200 200 200)
  (q/rect (:x state) (:y state) (:size state) (:size state)))

(comment
  (q/pixels)
  (q/map-range (q/noise 200) 0 1 (- 10) 10)
  [(q/mouse-x) (q/mouse-y)])