(ns core
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]
            [forces.friction :as sketch]))

; this function is called in index.html
(defn ^:export run-sketch []
  (q/defsketch app
    :host "app"
    :size [600 600]
    :renderer :p2d
    ; setup function called only once, during sketch initialization.
    :setup sketch/setup
    ; update-state is called on each iteration before draw-state.
    :update sketch/update-state
    :draw sketch/draw-state
    ; This sketch uses functional-mode middleware.
    ; Check quil wiki for more info about middlewares and particularly
    ; fun-mode.
    :middleware [m/fun-mode]))

;; start is called by init and after code reloading finishes
(defn ^:dev/after-load start []
  (js/console.log "start"))

(defn ^:export init []
  ;; init is called ONCE when the page loads
  ;; this is called in the index.html and must be exported
  ;; so it is available even in :advanced release builds
  (js/console.log "init")
  (run-sketch)
  (start))

;; this is called before any code is reloaded
(defn ^:dev/before-load stop []
  (js/console.log "stop"))

(comment
  (q/get-sketch-by-id "app")
  (.-length (. js/document getElementsByClassName "p5Canvas"))
  (.getElementById js/document "defaultCanvas0"))