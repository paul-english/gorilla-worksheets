;; gorilla-repl.fileformat = 1

;; **
;;; # Rolling Dice, and Flipping Coins
;; **

;; @@
(require '[clojure.math.combinatorics :refer [selections]])
(require '[clojure.pprint :refer [pprint]])
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; If @@X@@ is the product of two dice, what are it's probabilities. @@P(X=i)@@, for @@i=1,2,3,\dots@@
;; **

;; @@
(def probabilities (->> (map inc (range 6))             ; One die
                        (#(selections % 2))             ; All possible rolls from 2 dice
                        (map #(apply * %))              ; Compute products
                        frequencies                     ; Do some counting
                        (#(map (fn [[product freq]]     ; Make our frequencies into a ratio
                                (vector product (/ freq (* 2 (count %)))))
                               %))
						sort))
(pprint probabilities)

;; Ensure our distribution sums to 1
(apply + (map second probabilities))
;; @@
;; ->
;;; ([1 1/36]
;;;  [2 1/18]
;;;  [3 1/18]
;;;  [4 1/12]
;;;  [5 1/18]
;;;  [6 1/9]
;;;  [8 1/18]
;;;  [9 1/36]
;;;  [10 1/18]
;;;  [12 1/9]
;;;  [15 1/18]
;;;  [16 1/36]
;;;  [18 1/18]
;;;  [20 1/18]
;;;  [24 1/18]
;;;  [25 1/36]
;;;  [30 1/18]
;;;  [36 1/36])
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-bigint'>1N</span>","value":"1N"}
;; <=

;; **
;;; What if @@X@@ is the sum of 3 dice?
;; **

;; @@
(def probabilities (->> (map inc (range 6))
                        (#(selections % 3))
                        (map #(apply + %))
                        frequencies
                        (#(map (fn [[product freq]]
                                (vector product (/ freq 216))) ; 6^3 (the int avoids a decimal calc)
                               %))
                        sort))
(pprint probabilities)

;; Ensure our distribution sums to 1
(apply + (map second probabilities))
;; @@
;; ->
;;; ([3 1/216]
;;;  [4 1/72]
;;;  [5 1/36]
;;;  [6 5/108]
;;;  [7 5/72]
;;;  [8 7/72]
;;;  [9 25/216]
;;;  [10 1/8]
;;;  [11 1/8]
;;;  [12 25/216]
;;;  [13 7/72]
;;;  [14 5/72]
;;;  [15 5/108]
;;;  [16 1/36]
;;;  [17 1/72]
;;;  [18 1/216])
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-bigint'>1N</span>","value":"1N"}
;; <=

;; @@
(defn dice [n event-fn]
  {:post [(= 1 (apply + (map second %)))]}
  (->> (map inc (range 6))
       (#(selections % n))
       (map event-fn)
       frequencies
       (#(map (fn [[product freq]]
                (vector product (/ freq (Math/pow 6 n))))
              %))
       sort))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;user/dice</span>","value":"#'user/dice"}
;; <=

;; @@
(pprint (dice 3 #(apply + %)))
;; @@
;; ->
;;; ([3 0.004629629629629629]
;;;  [4 0.013888888888888888]
;;;  [5 0.027777777777777776]
;;;  [6 0.046296296296296294]
;;;  [7 0.06944444444444445]
;;;  [8 0.09722222222222222]
;;;  [9 0.11574074074074074]
;;;  [10 0.125]
;;;  [11 0.125]
;;;  [12 0.11574074074074074]
;;;  [13 0.09722222222222222]
;;;  [14 0.06944444444444445]
;;;  [15 0.046296296296296294]
;;;  [16 0.027777777777777776]
;;;  [17 0.013888888888888888]
;;;  [18 0.004629629629629629])
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(pprint (dice 10 #(apply + (filter odd? %))))
;; @@
;; ->
;;; ([0 9.765625E-4]
;;;  [1 0.0032552083333333335]
;;;  [2 0.0048828125]
;;;  [3 0.007595486111111111]
;;;  [4 0.012297453703703705]
;;;  [5 0.017288773148148147]
;;;  [6 0.025057066615226338]
;;;  [7 0.031158907750342937]
;;;  [8 0.03677849579903978]
;;;  [9 0.04594849854569934]
;;;  [10 0.05135327228234179]
;;;  [11 0.05792841935299497]
;;;  [12 0.06241175231587326]
;;;  [13 0.06274201960448103]
;;;  [14 0.06657862736350319]
;;;  [15 0.0646939538561195]
;;;  [16 0.062549019140883]
;;;  [17 0.06003157864654778]
;;;  [18 0.05317857375336585]
;;;  [19 0.04994891689529035]
;;;  [20 0.04325264425519484]
;;;  [21 0.037020366559975613]
;;;  [22 0.03217054771249302]
;;;  [23 0.02533614826245999]
;;;  [24 0.021558747158080576]
;;;  [25 0.016708283321902148]
;;;  [26 0.01282460792625616]
;;;  [27 0.01018437150713814]
;;;  [28 0.007077179810411692]
;;;  [29 0.005568237025605853]
;;;  [30 0.003805152156471744]
;;;  [31 0.002643461362597165]
;;;  [32 0.001919750969533777]
;;;  [33 0.00114609529797287]
;;;  [34 8.620521992328405E-4]
;;;  [35 4.9118369913123E-4]
;;;  [36 3.195174770106183E-4]
;;;  [37 2.0540409236396892E-4]
;;;  [38 1.0071746557943403E-4]
;;;  [39 7.739864349946654E-5]
;;;  [40 3.0711384824467815E-5]
;;;  [41 2.232653177869227E-5]
;;;  [42 1.0170975588070924E-5]
;;;  [43 4.465306355738455E-6]
;;;  [44 3.4730160544632424E-6]
;;;  [45 4.96145150637606E-7]
;;;  [46 9.095994428356111E-7]
;;;  [48 1.65381716879202E-7]
;;;  [50 1.65381716879202E-8])
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(defn difference [coin-tosses]
  (->> coin-tosses
       (map #(if (clojure.core/= :H %) 1 -1))
       (apply +)
       ;(#(Math/abs %))
       ))

(def n 3)

(->> (selections [:H :T] n)
     (map difference)
     frequencies
     (#(map (fn [[product freq]]
              (vector product (/ freq (Math/pow 2 n))))
            %))
     sort)

;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(<span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[<span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>-3</span>","value":"-3"},{"type":"html","content":"<span class='clj-double'>0.125</span>","value":"0.125"}],"value":"[-3 0.125]"},{"type":"list-like","open":"<span class='clj-vector'>[<span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>-1</span>","value":"-1"},{"type":"html","content":"<span class='clj-double'>0.375</span>","value":"0.375"}],"value":"[-1 0.375]"},{"type":"list-like","open":"<span class='clj-vector'>[<span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-double'>0.375</span>","value":"0.375"}],"value":"[1 0.375]"},{"type":"list-like","open":"<span class='clj-vector'>[<span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"},{"type":"html","content":"<span class='clj-double'>0.125</span>","value":"0.125"}],"value":"[3 0.125]"}],"value":"([-3 0.125] [-1 0.375] [1 0.375] [3 0.125])"}
;; <=

;; @@
(println (dice 2 #(apply max %)))

(def probabilities (->> (map inc (range 6))
                        (#(selections % 2))
                        (map #(apply max %))
                        frequencies
                        (#(map (fn [[product freq]]
                                (vector product (/ freq 36))) ; 6^3 (the int avoids a decimal calc)
                               %))
                        sort))
(pprint probabilities)

;; Ensure our distribution sums to 1
(apply + (map second probabilities))
;; @@
;; ->
;;; ([1 0.027777777777777776] [2 0.08333333333333333] [3 0.1388888888888889] [4 0.19444444444444445] [5 0.25] [6 0.3055555555555556])
;;; ([1 1/36] [2 1/12] [3 5/36] [4 7/36] [5 1/4] [6 11/36])
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-bigint'>1N</span>","value":"1N"}
;; <=

;; @@

;; @@
