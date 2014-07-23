;; gorilla-repl.fileformat = 1

;; **
;;; # An Example of Gaussian Quadrature
;; **

;; @@
(use 'gorilla-plot.core)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(def I (* 2 Math/PI
           (Math/log (/ (+ 1 (Math/sqrt 2))
                        2))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;user/I</span>","value":"#'user/I"}
;; <=

;; @@
I
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-double'>1.1826613914901567</span>","value":"1.1826613914901567"}
;; <=

;; @@
(defn gaussian-quadrature [f n]
  (* (/ Math/PI n)
     (->> (range n)
          (map inc)
          (map #(f (Math/cos (/ (* (- (* 2 %) 1)
                                   Math/PI)
                                (* 2 n)))))
          (apply +))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;user/gaussian-quadrature</span>","value":"#'user/gaussian-quadrature"}
;; <=

;; @@
(def n (range 4 9))
n
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-lazy-seq'>(<span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>4</span>","value":"4"},{"type":"html","content":"<span class='clj-long'>5</span>","value":"5"},{"type":"html","content":"<span class='clj-long'>6</span>","value":"6"},{"type":"html","content":"<span class='clj-long'>7</span>","value":"7"},{"type":"html","content":"<span class='clj-long'>8</span>","value":"8"}],"value":"(4 5 6 7 8)"}
;; <=

;; @@
(def results (map #(gaussian-quadrature (fn [x] 
                                          (Math/log (+ (Math/pow x 2) 1)))
                                        %) 
                  n))
results
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-lazy-seq'>(<span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-double'>1.1840219784143866</span>","value":"1.1840219784143866"},{"type":"html","content":"<span class='clj-double'>1.1824745448480443</span>","value":"1.1824745448480443"},{"type":"html","content":"<span class='clj-double'>1.182688104009816</span>","value":"1.182688104009816"},{"type":"html","content":"<span class='clj-double'>1.1826574630224815</span>","value":"1.1826574630224815"},{"type":"html","content":"<span class='clj-double'>1.1826619812548276</span>","value":"1.1826619812548276"}],"value":"(1.1840219784143866 1.1824745448480443 1.182688104009816 1.1826574630224815 1.1826619812548276)"}
;; <=

;; @@
(def error (->> results
                (map #(Math/abs (- I %)))
                (zipmap [4 5 6 7 8])))
error
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-map'>{<span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>8</span>","value":"8"},{"type":"html","content":"<span class='clj-double'>5.897646708774573E-7</span>","value":"5.897646708774573E-7"}],"value":"[8 5.897646708774573E-7]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>7</span>","value":"7"},{"type":"html","content":"<span class='clj-double'>3.92846767516275E-6</span>","value":"3.92846767516275E-6"}],"value":"[7 3.92846767516275E-6]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>6</span>","value":"6"},{"type":"html","content":"<span class='clj-double'>2.6712519659355394E-5</span>","value":"2.6712519659355394E-5"}],"value":"[6 2.6712519659355394E-5]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>5</span>","value":"5"},{"type":"html","content":"<span class='clj-double'>1.8684664211243707E-4</span>","value":"1.8684664211243707E-4"}],"value":"[5 1.8684664211243707E-4]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>4</span>","value":"4"},{"type":"html","content":"<span class='clj-double'>0.0013605869242299118</span>","value":"0.0013605869242299118"}],"value":"[4 0.0013605869242299118]"}],"value":"{8 5.897646708774573E-7, 7 3.92846767516275E-6, 6 2.6712519659355394E-5, 5 1.8684664211243707E-4, 4 0.0013605869242299118}"}
;; <=

;; @@
(list-plot error
           :plot-range [[3 9] :all])
;; @@
;; =>
;;; {"type":"vega","content":{"axes":[{"scale":"x","type":"x"},{"scale":"y","type":"y"}],"scales":[{"name":"x","type":"linear","range":"width","domain":[3,9]},{"name":"y","type":"linear","range":"height","nice":true,"domain":{"data":"45207a3c-4fcc-4b51-8663-2e4297b6b988","field":"data.y"}}],"marks":[{"type":"symbol","from":{"data":"45207a3c-4fcc-4b51-8663-2e4297b6b988"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"y":{"scale":"y","field":"data.y"},"fill":{"value":"steelblue"},"fillOpacity":{"value":1}},"update":{"shape":"circle","size":{"value":70},"stroke":{"value":"transparent"}},"hover":{"size":{"value":210},"stroke":{"value":"white"}}}}],"data":[{"name":"45207a3c-4fcc-4b51-8663-2e4297b6b988","values":[{"x":8,"y":5.897646708774573E-7},{"x":7,"y":3.92846767516275E-6},{"x":6,"y":2.6712519659355394E-5},{"x":5,"y":1.8684664211243707E-4},{"x":4,"y":0.0013605869242299118}]}],"width":400,"height":247.2187957763672,"padding":{"right":10,"top":10,"bottom":20,"left":50}},"value":"#gorilla_repl.vega.VegaView{:content {\"axes\" [{\"scale\" \"x\", \"type\" \"x\"} {\"scale\" \"y\", \"type\" \"y\"}], \"scales\" [{\"name\" \"x\", \"type\" \"linear\", \"range\" \"width\", \"domain\" [3 9]} {\"name\" \"y\", \"type\" \"linear\", \"range\" \"height\", \"nice\" true, \"domain\" {\"data\" \"45207a3c-4fcc-4b51-8663-2e4297b6b988\", \"field\" \"data.y\"}}], \"marks\" [{\"type\" \"symbol\", \"from\" {\"data\" \"45207a3c-4fcc-4b51-8663-2e4297b6b988\"}, \"properties\" {\"enter\" {\"x\" {\"scale\" \"x\", \"field\" \"data.x\"}, \"y\" {\"scale\" \"y\", \"field\" \"data.y\"}, \"fill\" {\"value\" \"steelblue\"}, \"fillOpacity\" {\"value\" 1}}, \"update\" {\"shape\" \"circle\", \"size\" {\"value\" 70}, \"stroke\" {\"value\" \"transparent\"}}, \"hover\" {\"size\" {\"value\" 210}, \"stroke\" {\"value\" \"white\"}}}}], \"data\" [{\"name\" \"45207a3c-4fcc-4b51-8663-2e4297b6b988\", \"values\" ({\"x\" 8, \"y\" 5.897646708774573E-7} {\"x\" 7, \"y\" 3.92846767516275E-6} {\"x\" 6, \"y\" 2.6712519659355394E-5} {\"x\" 5, \"y\" 1.8684664211243707E-4} {\"x\" 4, \"y\" 0.0013605869242299118})}], \"width\" 400, \"height\" 247.2188, \"padding\" {\"right\" 10, \"top\" 10, \"bottom\" 20, \"left\" 50}}}"}
;; <=

;; @@

;; @@
