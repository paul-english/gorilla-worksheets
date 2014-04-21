;; gorilla-repl.fileformat = 1

;; **
;;; # Factorization Machines
;;; 
;;; We have a design matrix @@X \in \mathbb{R}^{n \times p}@@, where the @@i@@th row @@x_i \in \mathbb{R}^p@@ of @@X@@ is one case with @@p@@ real valued variables, and @@y_i@@ is the prediction target.
;;; 
;;; $$
;;; \hat{y}(\mathbf{x}) := w_0 + \sum_{j=1}^{p} w_j x_j + \sum_{j=1}^{p} \sum_{j^\prime = j+1}^p x_j x_{j^\prime} \sum_{f=1}^k v_{j,f} v_{j^\prime, f} 
;;; $$
;;; 
;;; Where @@k@@ is the dimensionality of our factorization and all model parameters are @@\Theta = \\{w_0, w_1, \dots, w_p, v_{1,1}, \dots, v_{p,k}\\}@@
;;; 
;;; $$
;;; w_0 \in \mathbb{R}, \\,\\,\\,\\,\\,\\,\\,
;;; \mathbf{w} \in \mathbb{R}^p, \\,\\,\\,\\,\\,\\,\\,
;;; V \in \mathbb{R}^{p \times k}
;;; $$
;;; 
;;; Using `core.matrix` we can implement a @@2@@-degree factorization model as follows.
;; **

;; @@
(use 'clojure.core.matrix)
(use 'clojure.core.matrix.operators)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(def user (matrix [[1 0 0]
           [1 0 0]
           [1 0 0]
           [0 1 0]
           [0 1 0]
           [0 0 1]
           [0 0 1]]))
(def movie (matrix [[1 0 0 0]
            [0 1 0 0]
            [0 0 1 0]
            [0 0 1 0]
            [0 0 0 1]
            [1 0 0 0]
            [0 0 1 0]]))
(def other-movies-rated (matrix [[0.3 0.3 0.3 0.0]
                         [0.3 0.3 0.3 0.0]
                         [0.3 0.3 0.3 0.0]
                         [0.0 0.0 0.5 0.5]
                         [0.0 0.0 0.5 0.5]
                         [0.5 0.0 0.5 0.0]
                         [0.5 0.0 0.5 0.0]]))
(def time-since-last-rating (matrix [[13]
                             [14]
           [16]
           [5]
           [8]
           [9]
           [12]]))
(def last-movie-rated (matrix [[0 0 0 0]
                       [1 0 0 0]
                       [0 1 0 0]
                       [0 0 0 0]
                       [0 0 1 0]
                       [0 0 0 0]
                       [1 0 0 0]]))
(def target (matrix [[5]
             [3]
             [1]
             [4]
             [5]
             [1]
             [5]]))
;; NOTE: core.matrix hasn't implemented (join-along 1 ...)
(def design (transpose (join (transpose user) 
                             (transpose movie)
                             (transpose other-movies-rated)
                             (transpose time-since-last-rating)
                             (transpose last-movie-rated)
                             (transpose target))))
(pm design)
;; @@
;; ->
;;; [[1.000 0.000 0.000 1.000 0.000 0.000 0.000 0.300 0.300 0.300 0.000 13.000 0.000 0.000 0.000 0.000 5.000]
;;;  [1.000 0.000 0.000 0.000 1.000 0.000 0.000 0.300 0.300 0.300 0.000 14.000 1.000 0.000 0.000 0.000 3.000]
;;;  [1.000 0.000 0.000 0.000 0.000 1.000 0.000 0.300 0.300 0.300 0.000 16.000 0.000 1.000 0.000 0.000 1.000]
;;;  [0.000 1.000 0.000 0.000 0.000 1.000 0.000 0.000 0.000 0.500 0.500  5.000 0.000 0.000 0.000 0.000 4.000]
;;;  [0.000 1.000 0.000 0.000 0.000 0.000 1.000 0.000 0.000 0.500 0.500  8.000 0.000 0.000 1.000 0.000 5.000]
;;;  [0.000 0.000 1.000 1.000 0.000 0.000 0.000 0.500 0.000 0.500 0.000  9.000 0.000 0.000 0.000 0.000 1.000]
;;;  [0.000 0.000 1.000 0.000 0.000 1.000 0.000 0.500 0.000 0.500 0.000 12.000 1.000 0.000 0.000 0.000 5.000]]
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; ## Efficient Computation of the model
;;; 
;;; The above model can be simplified such that it has a complexity of @@\mathcal{O}(k N_z (\mathbf{x}))@@
;;; 
;;; $$
;;; \hat{y}(\mathbf{x}) = w_0 + \sum_{j=1}^{p} w_j x_j + \frac{1}{2} \sum_{f=1}^{k} \left[
;;; \left( 
;;; \sum_{j=1}^{p} v_{j,f} x_j
;;; \right)^2 
;;; - \sum_{j=1}^{p} v_{j,f}^2 x_j^2
;;; \right]
;;; $$
;; **

;; @@

(defn predict [x w_0 w V]
  ;; x is a vector of length p
  ;; w is our weighted parameters
  ;; 
  )

(defn squared-error [Yn Y]
  (-> (- Yn Y)
      (** 2)))


(defn stochastic-gradient-descent [X 
                                   regularization-params 
                                   learning-rate 
                                   initialization
                                   & {steps :steps
                                      alpha :alpha
                                      loss :loss-fn
                                      :as opts
                                      :or {steps 5000
                                           alpha 0.0002
                                           loss squared-error}}]
  (loop [step 0]
    (let [
          
          ;; calculate w_0
          ;; calculate w_i
          ;; calculate V
          ]
      (if (or (< error 0.1)
            (> step steps))
        {:params {}}
        (recur (inc step)))
      )
    
    )
  )
;; @@

;; **
;;; ## The Gradient
;;; 
;;; The gradient of our factorization model is a very simple piecewise function.
;;; 
;;; Note: mathjax doesn't like the piecewise latex
;;; 
;;; $$
;;; \frac{\partial}{\partial \theta} \hat{y} (\mathbf{x}) =
;;; 1, \text{if} \theta \text{is} w_0
;;; $$
;;; $$
;;; x_i, \text{if} \theta \text{is} w_i
;;; $$
;;; $$
;;; x_i \sum_{j=1}^{n} v_{j,f} x_j - v_{i,j} x_i^2, \text{if} \theta \text{is} v_{i,f} \\
;;; $$
;; **

;; @@
(defn gradient []
  (case theta
    "w0"
    "wi" (nth x i)
    "vif" (* (nth x i)
             ())))
;; @@
