;; gorilla-repl.fileformat = 1

;; **
;;; # Discrete Time Markov Chains
;;; 
;;; A simple example
;;; <http://austingwalters.com/introduction-to-markov-processes/>
;; **

;; @@
(use 'clojure.core.matrix)
(use 'clojure.core.matrix.operators)
(use 'gorilla-plot.core)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(def transitions (matrix [[0.5 0.5 0.0 0.0 0.0 0.0]
                          [0.4 0.1 0.5 0.0 0.0 0.0]
                          [0.0 0.3 0.2 0.5 0.0 0.0]
                          [0.0 0.0 0.2 0.3 0.5 0.0]
                          [0.0 0.0 0.0 0.1 0.4 0.5]
                          [0.0 0.0 0.0 0.0 0.6 0.4]]))

(pm transitions)
(println (shape transitions))
;; @@
;; ->
;;; [[0.500 0.500 0.000 0.000 0.000 0.000]
;;;  [0.400 0.100 0.500 0.000 0.000 0.000]
;;;  [0.000 0.300 0.200 0.500 0.000 0.000]
;;;  [0.000 0.000 0.200 0.300 0.500 0.000]
;;;  [0.000 0.000 0.000 0.100 0.400 0.500]
;;;  [0.000 0.000 0.000 0.000 0.600 0.400]]
;;; [6 6]
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(def initial-state [1 0 0 0 0 0])

(defn markov-process [transition state]
  (mmul state transition))

(def markov-sequence (iterate (partial markov-process transitions) 
                              initial-state))

(clojure.pprint/pprint (nth markov-sequence 10))
;; @@
;; ->
;;; [0.1417915245
;;;  0.1402372605
;;;  0.13828209
;;;  0.1437719375
;;;  0.2589021875
;;;  0.17701499999999998]
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(bar-chart '[0 1 2 3 4 5]
           (nth markov-sequence 100))
;; @@
;; =>
;;; {"type":"vega","content":{"axes":[{"scale":"x","type":"x"},{"scale":"y","type":"y"}],"scales":[{"name":"x","type":"ordinal","range":"width","domain":{"data":"1512574a-32a4-4642-bf86-f4189ddf036d","field":"data.x"}},{"name":"y","range":"height","nice":true,"domain":{"data":"1512574a-32a4-4642-bf86-f4189ddf036d","field":"data.y"}}],"marks":[{"type":"rect","from":{"data":"1512574a-32a4-4642-bf86-f4189ddf036d"},"properties":{"enter":{"width":{"offset":-1,"scale":"x","band":true},"x":{"scale":"x","field":"data.x"},"y":{"scale":"y","field":"data.y"},"y2":{"scale":"y","value":0}},"update":{"fill":{"value":"steelblue"},"opacity":{"value":1}},"hover":{"fill":{"value":"#FF29D2"}}}}],"data":[{"name":"1512574a-32a4-4642-bf86-f4189ddf036d","values":[{"x":0,"y":0.017458306432539608},{"x":1,"y":0.021822400674246463},{"x":2,"y":0.03636940928255807},{"x":3,"y":0.0909208351237625},{"x":4,"y":0.4545979507387965},{"x":5,"y":0.37883109774809787}]}],"width":400,"height":247.2187957763672,"padding":{"right":10,"top":10,"bottom":20,"left":50}},"value":"#gorilla_repl.vega.VegaView{:content {\"axes\" [{\"scale\" \"x\", \"type\" \"x\"} {\"scale\" \"y\", \"type\" \"y\"}], \"scales\" [{\"name\" \"x\", \"type\" \"ordinal\", \"range\" \"width\", \"domain\" {\"data\" \"1512574a-32a4-4642-bf86-f4189ddf036d\", \"field\" \"data.x\"}} {\"name\" \"y\", \"range\" \"height\", \"nice\" true, \"domain\" {\"data\" \"1512574a-32a4-4642-bf86-f4189ddf036d\", \"field\" \"data.y\"}}], \"marks\" [{\"type\" \"rect\", \"from\" {\"data\" \"1512574a-32a4-4642-bf86-f4189ddf036d\"}, \"properties\" {\"enter\" {\"width\" {\"offset\" -1, \"scale\" \"x\", \"band\" true}, \"x\" {\"scale\" \"x\", \"field\" \"data.x\"}, \"y\" {\"scale\" \"y\", \"field\" \"data.y\"}, \"y2\" {\"scale\" \"y\", \"value\" 0}}, \"update\" {\"fill\" {\"value\" \"steelblue\"}, \"opacity\" {\"value\" 1}}, \"hover\" {\"fill\" {\"value\" \"#FF29D2\"}}}}], \"data\" [{\"name\" \"1512574a-32a4-4642-bf86-f4189ddf036d\", \"values\" ({\"x\" 0, \"y\" 0.017458306432539608} {\"x\" 1, \"y\" 0.021822400674246463} {\"x\" 2, \"y\" 0.03636940928255807} {\"x\" 3, \"y\" 0.0909208351237625} {\"x\" 4, \"y\" 0.4545979507387965} {\"x\" 5, \"y\" 0.37883109774809787})}], \"width\" 400, \"height\" 247.2188, \"padding\" {\"right\" 10, \"top\" 10, \"bottom\" 20, \"left\" 50}}}"}
;; <=

;; @@

;; @@
