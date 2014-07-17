;; gorilla-repl.fileformat = 1

;; **
;;; # Machine Epsilon
;;; 
;;; Finding the precision of your machine, or platforms arithmetic.
;; **

;; @@
(defn machine-epsilon []
  (loop [k 1 s 1.0]
    (if (or (<= (+ s 1.0) 1.0)
            (> k 100))
      [(dec k), (* 2.0 s)]
      (recur (inc k) (* 0.5 s)))))

;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;user/machine-epsilon</span>","value":"#'user/machine-epsilon"}
;; <=

;; @@
(machine-epsilon)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[<span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>53</span>","value":"53"},{"type":"html","content":"<span class='clj-double'>2.220446049250313E-16</span>","value":"2.220446049250313E-16"}],"value":"[53 2.220446049250313E-16]"}
;; <=

;; @@

;; @@
