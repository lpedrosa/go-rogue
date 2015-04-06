(ns go-rogue.core
  (:require [lanterna.screen :as s]))

(defn draw-square 
  ([screen x y] (draw-square screen x y 5))
  ([screen x y size]
    (let [end-x (+ x (inc size))
          end-y (-> size
                    (quot 2)
                    inc
                    (+ y))]
      (loop [cur-x x
             cur-y y]
        (when (< cur-y end-y)
          (s/put-string screen cur-x cur-y " " {:bg :blue})
          (let [x-in-range (< cur-x end-x)
                new-x (if x-in-range (inc cur-x) x)
                new-y (if-not x-in-range (inc cur-y) cur-y)]
            (recur new-x new-y)))))))

(defn simple-draw 
  [screen-type]
  (let [screen (s/get-screen screen-type)]
    (s/in-screen screen
                 (s/put-string screen 0 0 "Wtf is this shit" {:fg :red})
                 (s/put-string screen 0 1 "I assume this means row 2" {:bg :yellow})
                 (s/put-string screen 20 2 "Should start a column 20")
                 (draw-square screen 20 15)
                 (draw-square screen 27 20 10)
                 (s/redraw screen)
                 (s/get-key-blocking screen))))

(defn -main 
  [& args]
  (let [args (set args)
        screen-type (cond
                      (args ":swing") :swing
                      (args ":text")  :text
                      :else           :auto)]
    (simple-draw screen-type)))
