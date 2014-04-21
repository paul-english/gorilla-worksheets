;; gorilla-repl.fileformat = 1

;; **
;;; # Matrix Factorization in Clojure w/ core.matrix
;;;
;;;
;;; This is based off the example provided by quuxlabs.
;;;
;;; <http://www.quuxlabs.com/blog/2010/09/matrix-factorization-a-simple-tutorial-and-implementation-in-python/>
;; **

;; @@
(refer-clojure :exclude '[+ - * / ==])
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(use 'clojure.core.matrix)
(use 'clojure.core.matrix.operators)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
;; taken from core.matrix page rank example
(def links
;; link matrix: each row represents the number of
;; outbound links from a page to other pages
  [[0 0 1 1 0 0 1 2 0 0]
   [1 0 0 1 0 0 0 0 0 0]
   [0 0 0 2 0 0 0 0 1 0]
   [0 1 0 0 0 0 1 0 0 0]
   [0 0 0 1 0 0 0 2 0 0]
   [2 0 0 0 0 0 0 0 1 0]
   [0 0 0 1 0 1 0 2 0 0]
   [0 0 1 4 0 0 0 0 0 0]
   [0 0 0 2 0 0 0 1 0 0]
   [1 1 1 1 0 1 0 2 1 0]])
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;user/links</span>","value":"#'user/links"}
;; <=

;; @@
(pm links)
;; @@
;; ->
;;; [[0.000 0.000 1.000 1.000 0.000 0.000 1.000 2.000 0.000 0.000]
;;;  [1.000 0.000 0.000 1.000 0.000 0.000 0.000 0.000 0.000 0.000]
;;;  [0.000 0.000 0.000 2.000 0.000 0.000 0.000 0.000 1.000 0.000]
;;;  [0.000 1.000 0.000 0.000 0.000 0.000 1.000 0.000 0.000 0.000]
;;;  [0.000 0.000 0.000 1.000 0.000 0.000 0.000 2.000 0.000 0.000]
;;;  [2.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 1.000 0.000]
;;;  [0.000 0.000 0.000 1.000 0.000 1.000 0.000 2.000 0.000 0.000]
;;;  [0.000 0.000 1.000 4.000 0.000 0.000 0.000 0.000 0.000 0.000]
;;;  [0.000 0.000 0.000 2.000 0.000 0.000 0.000 1.000 0.000 0.000]
;;;  [1.000 1.000 1.000 1.000 0.000 1.000 0.000 2.000 1.000 0.000]]
;;;
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(defn rand-matrix [n m]
  (->> (zero-matrix n m)
       (emap (fn [_] (rand)))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;user/rand-matrix</span>","value":"#'user/rand-matrix"}
;; <=

;; @@
(def k 2)
(def R (matrix [[5,3,0,1]
     			[4,0,0,1]
     			[1,1,0,5]
     			[1,0,0,4]
     			[0,1,5,4]]))
(def N (nth (shape R) 0))
(def M (nth (shape R) 1))
(def P (rand-matrix N k))
(def Q (rand-matrix M k))
(pm Q)
(pm P)
;; @@
;; ->
;;; [[0.249 0.652]
;;;  [0.548 0.055]
;;;  [0.226 0.831]
;;;  [0.166 0.691]]
;;; [[0.837 0.226]
;;;  [0.111 0.327]
;;;  [0.685 0.357]
;;;  [0.333 0.566]
;;;  [0.685 0.961]]
;;;
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; ## Estimation of R
;;; $$
;;; \hat{r}_{ij} = p_i^\intercal q_j = \sum_{k=1}^{K} p_{ik}q_{kj}
;;; $$
;; **

;; **
;;; ## Gradient Update
;;; $$
;;; p_{ik}^\prime = p_{ik} + \alpha \frac{\partial}{\partial p_{ik}} e_{ij}^2 = p_{ik} + 2 \alpha e_{ij}q_{kj}
;;; $$
;;; $$
;;; q_{kj}^\prime = q_{kj} + \alpha \frac{\partial}{\partial q_{kj}} e_{ij}^2 = q_{kj} + 2 \alpha e_{ij}p_{ik}
;;; $$
;; **

;; @@
(defn gradient-update [P Q E alpha beta]
  (+ P (* alpha (- (mmul 2 E Q)
                   (* beta P)))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;user/gradient-update</span>","value":"#'user/gradient-update"}
;; <=

;; **
;;; ## Regularized Error Function
;;; $$
;;; e_{ij}^2 = (r_{ij} - \sum_{k=1}^K p_{ik} q_{kj})^2 + \frac{\beta}{2} \sum_{k=1}^{K} (\|P\|^2 + \|Q\|^2)
;;; $$
;;;
;;; ## Overall Error
;;;
;;; $$
;;; \begin{align}
;;; E^2 = \sum_{(u_i, d_j, r_{ij}) \in T} e_{ij}^2 \\
;;; &= \sum_{(u_i, d_j, r_{ij}) \in T} (r_{ij} - \sum_{k=1}^K p_{ik}q_{kj})^2
;;; \end{align}
;;; $$
;; **

;; @@
(defn squared-error [R Rn P Q beta]
  (+ (esum (** (- R Rn) 2))
     (* (/ beta 2)
        (+ (esum (** P 2))
           (esum (** Q 2))))))

;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;user/squared-error</span>","value":"#'user/squared-error"}
;; <=

;; **
;;; ## Matrix Factorization w/ Solver tightly coupled
;; **

;; @@
(defn matrix-factorization [R P Q & {steps :steps alpha :alpha beta :beta
                                       :as opts
                                       :or {steps 5000 alpha 0.0002 beta 0.02}}]
  (loop [P P Q Q step 0]
    (let [E (- R (dot P (transpose Q)))
          Pn (gradient-update P Q E alpha beta)
          Qn (gradient-update Q P (transpose E) alpha beta)
          Rn (dot Pn (transpose Qn))
          error (squared-error R Rn Pn Qn beta)]
      (if (or (< error 0.001)
              (> step steps))
        [Pn Qn]
        (recur Pn Qn (inc step))))))

;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;user/matrix-factorization</span>","value":"#'user/matrix-factorization"}
;; <=

;; @@
(def res (matrix-factorization R P Q))
(pm R)
(pm (dot P (transpose Q)))
(def Pn (res 0))
(def Qn (res 1))
;; @@
;; ->
;;; [[5.000 3.000 0.000 1.000]
;;;  [4.000 0.000 0.000 1.000]
;;;  [1.000 1.000 0.000 5.000]
;;;  [1.000 0.000 0.000 4.000]
;;;  [0.000 1.000 5.000 4.000]]
;;; [[0.356 0.471 0.377 0.295]
;;;  [0.241 0.079 0.297 0.245]
;;;  [0.404 0.395 0.452 0.361]
;;;  [0.452 0.214 0.546 0.447]
;;;  [0.798 0.428 0.954 0.778]]
;;;
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;user/Qn</span>","value":"#'user/Qn"}
;; <=

;; @@
(pm Pn)
(pm Qn)
(pm (dot Pn (transpose Qn)))
;; @@
;; ->
;;; [[2.204 -0.017]
;;;  [1.475  0.008]
;;;  [0.799  1.414]
;;;  [0.609  1.104]
;;;  [0.006  2.150]]
;;; [[ 2.323 -0.215]
;;;  [ 0.865  0.250]
;;;  [-0.317  1.442]
;;;  [ 0.727  2.391]]
;;; [[ 5.124 1.903 -0.724 1.563]
;;;  [ 3.426 1.279 -0.457 1.091]
;;;  [ 1.551 1.045  1.785 3.961]
;;;  [ 1.178 0.804  1.399 3.083]
;;;  [-0.449 0.543  3.100 5.146]]
;;;
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
;; NOTE: why generate P & Q here? can't we do that within matrix-factorization?
(defn predict [A & {k :k
                    :as opts
                    :or {k 2}}]
  (let [n (nth (shape A) 0)
        m (nth (shape A) 1)
        P (rand-matrix n k)
        Q (rand-matrix m k)
        [Pn Qn] (matrix-factorization A P Q)]
    (dot Pn (transpose Qn))))

;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;user/predict</span>","value":"#'user/predict"}
;; <=

;; @@
(pm links)
;; @@
;; ->
;;; [[0.000 0.000 1.000 1.000 0.000 0.000 1.000 2.000 0.000 0.000]
;;;  [1.000 0.000 0.000 1.000 0.000 0.000 0.000 0.000 0.000 0.000]
;;;  [0.000 0.000 0.000 2.000 0.000 0.000 0.000 0.000 1.000 0.000]
;;;  [0.000 1.000 0.000 0.000 0.000 0.000 1.000 0.000 0.000 0.000]
;;;  [0.000 0.000 0.000 1.000 0.000 0.000 0.000 2.000 0.000 0.000]
;;;  [2.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 1.000 0.000]
;;;  [0.000 0.000 0.000 1.000 0.000 1.000 0.000 2.000 0.000 0.000]
;;;  [0.000 0.000 1.000 4.000 0.000 0.000 0.000 0.000 0.000 0.000]
;;;  [0.000 0.000 0.000 2.000 0.000 0.000 0.000 1.000 0.000 0.000]
;;;  [1.000 1.000 1.000 1.000 0.000 1.000 0.000 2.000 1.000 0.000]]
;;;
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(pm (predict links))
;; @@
;; ->
;;; [[0.265  0.283 0.477  1.041 -0.002  0.492  0.250  1.965 0.251  0.000]
;;;  [0.078 -0.001 0.177  1.036  0.001 -0.006 -0.003  0.037 0.108 -0.000]
;;;  [0.145 -0.013 0.333  2.019  0.001 -0.032 -0.016 -0.007 0.206 -0.000]
;;;  [0.021  0.032 0.033 -0.003 -0.000  0.057  0.029  0.218 0.016  0.000]
;;;  [0.233  0.253 0.416  0.876 -0.002  0.440  0.223  1.751 0.219  0.000]
;;;  [0.034  0.019 0.070  0.292  0.000  0.031  0.016  0.138 0.040 -0.000]
;;;  [0.252  0.283 0.447  0.869 -0.002  0.493  0.250  1.958 0.233  0.000]
;;;  [0.290 -0.019 0.662  3.975  0.003 -0.051 -0.026  0.030 0.408 -0.000]
;;;  [0.216  0.112 0.439  1.871  0.000  0.189  0.096  0.839 0.253 -0.000]
;;;  [0.310  0.331 0.558  1.222 -0.002  0.575  0.291  2.295 0.294  0.000]]
;;;
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(pm (predict links :k 10))
;; @@
;; ->
;;; [[ 0.064  0.111  0.911  1.028  0.032 -0.009  0.921  2.016 -0.094  0.059]
;;;  [ 0.989 -0.035 -0.085  1.016 -0.051  0.103  0.070 -0.018  0.056  0.067]
;;;  [ 0.165  0.180  0.093  2.003  0.127  0.030 -0.088 -0.022  0.615 -0.078]
;;;  [-0.035  0.936  0.058 -0.019  0.013 -0.016  0.980 -0.001  0.051  0.003]
;;;  [-0.023 -0.126  0.049  0.984  0.008  0.199  0.101  1.925  0.025 -0.019]
;;;  [ 1.978  0.017  0.031 -0.009  0.029 -0.077 -0.067  0.021  1.008 -0.017]
;;;  [-0.006  0.078  0.053  0.990 -0.003  0.797 -0.087  2.048 -0.003 -0.054]
;;;  [-0.076 -0.074  0.959  3.989 -0.060 -0.032  0.031  0.018  0.178  0.034]
;;;  [-0.035 -0.049  0.011  1.991 -0.016 -0.030  0.020  1.007  0.075 -0.010]
;;;  [ 0.988  0.959  0.976  1.004 -0.029  1.042  0.069  1.985  1.030  0.014]]
;;;
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; ## Normalization of matrix factorization based rating predictions
;;;
;;; Matrix factorization provides us with an approximate relation between different users and ratings. It's values aren't exact.
;;;
;;; We may want to ensure that our predictions are once again within the rating range.
;;;
;;; $$
;;; 5 \frac{(R + |min(R)|)}{max(R + |min(R)|)}
;;; $$
;; **

;; @@
(def Rn (dot Pn (transpose Qn)))

(pm R)
(pm Rn)
(pm (let [shifted (+ Rn
                     (abs (ereduce min Rn)))]
  (emap round
        (* (/ shifted
              (ereduce max shifted))
           5))))
;; @@
;; ->
;;; [[5.000 3.000 0.000 1.000]
;;;  [4.000 0.000 0.000 1.000]
;;;  [1.000 1.000 0.000 5.000]
;;;  [1.000 0.000 0.000 4.000]
;;;  [0.000 1.000 5.000 4.000]]
;;; [[ 5.124 1.903 -0.724 1.563]
;;;  [ 3.426 1.279 -0.457 1.091]
;;;  [ 1.551 1.045  1.785 3.961]
;;;  [ 1.178 0.804  1.399 3.083]
;;;  [-0.449 0.543  3.100 5.146]]
;;; [[5.000 2.000 0.000 2.000]
;;;  [4.000 2.000 0.000 2.000]
;;;  [2.000 2.000 2.000 4.000]
;;;  [2.000 1.000 2.000 3.000]
;;;  [0.000 1.000 3.000 5.000]]
;;;
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@

;; @@
