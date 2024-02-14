(ns forces.cursor-wind
  (:require [util.vector :as uv]
            [util.movable :as um]
            [quil.core :as q]))

(defn create-movable [pos vel mass]
  {:pos pos
   :mass mass
   :vel vel})

(defn setup []
  (q/frame-rate 50)
  (q/color-mode :hsl)
  {:entities [(create-movable {:x 10 :y 10} {:x 5 :y 9} 10) (create-movable {:x 30 :y 30} {:x 9 :y 4} 20) (create-movable {:x 40 :y 40} {:x 9 :y 4} 40)]})

(defn apply-wind [pos mass mouse-vec]
  (let [distance (uv/distance pos mouse-vec)
        direction (uv/direction pos mouse-vec)
        power (* 1000 (/ (/ 1 distance) mass))]
    {:x (* (:x direction) power) :y (* (:y direction) power)}))

(defn update-state [{:keys [entities]}]
  (let [new-entities (map
                      (fn [entity]
                        (let [{:keys [pos vel mass]} entity
                              mouse-x (q/mouse-x)
                              mouse-y (q/mouse-y)
                              width (q/width)
                              height (q/height)
                              new-vel (um/update-vel pos (uv/limit-vec (uv/add-vec vel (uv/add-vec (apply-wind pos mass {:x mouse-x :y mouse-y}) {:x 0 :y 2})) 35) mass width height)]
                          {:pos (um/limit-pos (uv/add-vec pos new-vel) mass width height) :vel new-vel :mass mass})) entities)]
    {:entities new-entities}))

(defn draw-state [{:keys [entities]}]
  (q/background 130 80 90)
  (dorun
   (map
    (fn [entity]
      (let [{{:keys [x y]} :pos mass :mass} entity]
        (q/ellipse x y mass mass))) entities)))

(comment
  (q/with-sketch (q/get-sketch-by-id "app")
    (q/no-loop))
  (q/with-sketch (q/get-sketch-by-id "app")
    (q/start-loop))
  (q/with-sketch (q/get-sketch-by-id "app")
    (apply-wind {:x 10 :y 10} 10 {:x 11 :y 11}))
  (q/with-sketch (q/get-sketch-by-id "app")
    (apply-wind {:x 50 :y 50} 20 {:x 50 :y 50}))
  (q/with-sketch (q/get-sketch-by-id "app")
    (swap! (q/state-atom) assoc :pos {:x 1 :y 1})))