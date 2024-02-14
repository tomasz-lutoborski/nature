(ns util.movable)

(defn update-vel [{pos-x :x pos-y :y} {vel-x :x vel-y :y} mass width height]
  (let [radius (/ mass 2)
        in-bounds-x? (and (> (- pos-x radius) 0) (< (+ pos-x radius) width))
        in-bounds-y? (and (> (- pos-y radius) 0) (< (+ pos-y radius) height))
        c 0.9]
    (cond
      (and in-bounds-x? in-bounds-y?) {:x vel-x :y vel-y}
      in-bounds-x? {:x vel-x :y (* (- c) vel-y)}
      in-bounds-y? {:x (* (- c) vel-x) :y vel-y}
      :else {:x (* (- c) vel-x) :y (* (- c) vel-y)})))

(defn limit-pos [{:keys [x y]} mass width height]
  (let [radius (/ mass 2)
        new-x (cond
                (< (- x radius) 0) radius
                (> (+ x radius) width) (- width radius)
                :else x)
        new-y (cond
                (< (- y radius) 0) radius
                (> (+ y radius) height) (- height radius)
                :else y)]
    {:x new-x :y new-y}))