;; gorilla-repl.fileformat = 1

;; **
;;; # Calculating the Euclidean Norm
;;; 
;;; The Euclidean norm is a very simple function that is widely used to measure distance between two points in any dimension. Usually you'll want to use an optimized library, but it's fun and quick to implement it yourself.
;;; 
;;; $$
;;; ||x||_2 = \sqrt{\sum_{i=1}^{n} x_i^2}
;;; $$
;; **

;; @@
(defn norm [x]
  {:pre [(coll? x)]}
  (->> x
       (map #(Math/pow % 2))
       (apply +)
       Math/sqrt))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;user/norm</span>","value":"#'user/norm"}
;; <=

;; @@
(println "(norm [1 2 3]):    " (norm [1 2 3]))
(println "(norm (range 20)): " (norm (range 20)))
(println "(norm [1 1 1]):    " (norm [1 1 1]))
(println "(norm [1 0 0]):    " (norm [1 0 0]))
(println "(norm [1 0 0]):    " (norm [3 4]))
;; @@
;; ->
;;; (norm [1 2 3]):     3.7416573867739413
;;; (norm (range 20)):  49.69909455915671
;;; (norm [1 1 1]):     1.7320508075688772
;;; (norm [1 0 0]):     1.0
;;; (norm [1 0 0]):     5.0
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@

;; @@
