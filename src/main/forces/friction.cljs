(ns forces.friction
  (:require [util.vector :as uv]
            [util.movable :as um]
            [quil.core :as q]
            [clojure.math :as m]))

(def water-bound 200)
(defn create-movable [pos vel mass]
  {:pos pos
   :vel vel
   :mass mass})

(defn setup []
  (q/frame-rate 50)
  (q/color-mode :hsl)
  (let [movables (repeatedly 10
                             (fn []
                               (let [mass (m/round (q/random 10 40))
                                     rand-x (m/round (q/random (- 600 mass)))
                                     pos {:x rand-x :y 50}
                                     vel {:x 0 :y 0}]
                                 (create-movable pos vel mass))))]
    {:entities movables}))

(defn update-state [{:keys [entities]}]
  (let [new-entities
        (map
         (fn [{:keys [pos vel mass]}]
           (let [width (q/width)
                 height (q/height)
                 new-vel (uv/limit-vec (um/update-vel pos (uv/add-vec vel {:x 0 :y 0.3}) mass width height) 30)]
             {:pos (um/limit-pos (uv/add-vec pos new-vel) mass width height)
              :vel new-vel
              :mass mass})) entities)]
    {:entities new-entities}))

(defn draw-state [{:keys [entities]}]
  (q/clear)
  (q/background 120 100 100)
  (q/stroke 120 100 20)
  (q/stroke-weight 3)
  (q/fill 140 100 100)
  (q/rect 0 (- (q/height) water-bound) 600 water-bound)
  (q/fill 40 100 100)
  (dorun
   (map
    (fn [{:keys [pos mass]}]
      (q/ellipse (:x pos) (:y pos) mass mass)) entities)))

(comment
  (q/with-sketch (q/get-sketch-by-id "app")
    (q/clear))
  (q/with-sketch (q/get-sketch-by-id "app")
    (q/no-loop))
  (q/with-sketch (q/get-sketch-by-id "app")
    (q/state))
  (q/with-sketch (q/get-sketch-by-id "app")
    (q/start-loop)))