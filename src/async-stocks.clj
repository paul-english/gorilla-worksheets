;; gorilla-repl.fileformat = 1

;; **
;;; http://onbeyondlambda.blogspot.se/2014/03/simulated-stock-ticker-with-coreasync.html?utm_source=dlvr.it&utm_medium=twitter
;; **

;; @@
(ns ticker.core
  (:require [clojure.core.async
             :refer [chan timeout go]
              :as async]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(defn adjust-price [old-price]
  (let  [numerator (- (rand-int 30) 15)
         adjustment (* numerator 0.01M)]
    (+ old-price adjustment)))

(defn random-time [t]
  (* t (+ 1 (rand-int 5))))

(defn new-transaction [symbol price]
  {:symbol symbol
   :time (java.util.Date.) 
   :price price})
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;ticker.core/new-transaction</span>","value":"#'ticker.core/new-transaction"}
;; <=

;; @@
(defn make-ticker [symbol t start-price]
  (let [c (chan)]
    (go
     (loop [price start-price]
       (let [new-price (adjust-price price)]
         (<! (timeout (random-time t)))
         (>! c (new-transaction symbol new-price))
         (recur new-price))))
    c))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;ticker.core/make-ticker</span>","value":"#'ticker.core/make-ticker"}
;; <=

;; @@
(def stocks [ ;; symbol min-interval starting-price
             ["AAPL" 1400 537 ]
             ["AMZN" 4200 345]
             ["CSCO" 400  22]
             ["EBAY" 1200 55]
             ["GOOG" 8200 1127]
             ["IBM" 2200  192]
             ["MSFT" 500 40]
             ["ORCL" 1000 39]
             ["RHT" 10200  53]
             ["T" 600 35]])
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;ticker.core/stocks</span>","value":"#'ticker.core/stocks"}
;; <=

;; @@
(defn run-sim []
  (let [ticker (async/merge
                (map #(apply make-ticker %) stocks))]
    (go
     (loop [x 0]
       (when (< x 1000)
         (do (println (str x "-" (<! ticker)))
             (recur (inc x))))))))

;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;ticker.core/run-sim</span>","value":"#'ticker.core/run-sim"}
;; <=

;; @@
(run-sim)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>#&lt;ManyToManyChannel clojure.core.async.impl.channels.ManyToManyChannel@564b0577&gt;</span>","value":"#<ManyToManyChannel clojure.core.async.impl.channels.ManyToManyChannel@564b0577>"}
;; <=
