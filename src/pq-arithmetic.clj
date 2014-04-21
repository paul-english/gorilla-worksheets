;; gorilla-repl.fileformat = 1

;; **
;;; # GEB: The PQ- System
;; **

;; @@
(use 'clojure.core.logic)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(defne hypheno [x]
  ([["-"]])
  ([["-" . ?d]] (hypheno ?d)))

(def out
  (run 10 [a]
    (fresh [x1 y1 x y z] 
       (hypheno x1)
       (hypheno y1)
       (project [x1 y1] 
                (== z (clojure.string/join (concat x1 y1)))
                (== x (clojure.string/join x1))
                (== y (clojure.string/join y1))
                )
       (== a [x "p" y "q" z])
      )))

(clojure.pprint/pprint out)
;; how can we setup
;; @@
;; ->
;;; ([&quot;-&quot; &quot;p&quot; &quot;-&quot; &quot;q&quot; &quot;--&quot;]
;;;  [&quot;-&quot; &quot;p&quot; &quot;--&quot; &quot;q&quot; &quot;---&quot;]
;;;  [&quot;--&quot; &quot;p&quot; &quot;-&quot; &quot;q&quot; &quot;---&quot;]
;;;  [&quot;-&quot; &quot;p&quot; &quot;---&quot; &quot;q&quot; &quot;----&quot;]
;;;  [&quot;-&quot; &quot;p&quot; &quot;----&quot; &quot;q&quot; &quot;-----&quot;]
;;;  [&quot;--&quot; &quot;p&quot; &quot;--&quot; &quot;q&quot; &quot;----&quot;]
;;;  [&quot;-&quot; &quot;p&quot; &quot;-----&quot; &quot;q&quot; &quot;------&quot;]
;;;  [&quot;-&quot; &quot;p&quot; &quot;------&quot; &quot;q&quot; &quot;-------&quot;]
;;;  [&quot;---&quot; &quot;p&quot; &quot;-&quot; &quot;q&quot; &quot;----&quot;]
;;;  [&quot;--&quot; &quot;p&quot; &quot;---&quot; &quot;q&quot; &quot;-----&quot;])
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@

;; @@
;; ->
;;; (&quot;-p-q--&quot;
;;;  &quot;-p--q---&quot;
;;;  &quot;--p-q---&quot;
;;;  &quot;-p---q----&quot;
;;;  &quot;-p----q-----&quot;
;;;  &quot;--p--q----&quot;
;;;  &quot;-p-----q------&quot;
;;;  &quot;-p------q-------&quot;
;;;  &quot;---p-q----&quot;
;;;  &quot;--p---q-----&quot;)
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(defne str-hypheno [x]
  (["-"])
  ([_] (project [x] 
            (let [s (str x)]
              (== true (.startsWith s "-"))
              (hypheno (subs s 1 (count s)))
              
              )
            )
   ;(hypheno ?d)
   )
  )

(def forms
  (run 10 [q]
    (fresh [x y] 

           ;(== q [x "p" y "q" x y])
           (project [x y] 
                    ;; x y & are lvar from fresh and can't seem to be expressed as strings
                    (== q (->> [x "p" y "q" x y]
                               flatten
                               )))
           
	       (hypheno x)
    	   (hypheno y))))

(clojure.pprint/pprint forms)

(println "------")

(def ins-outs (run 10 [x y a]
                   

            (hypheno x)
            (hypheno y)
            (project [x y]
                     (== a (->> [x "p" y "q" x y] 
                                flatten 
                                clojure.string/join))
                     )

              ))

(clojure.pprint/pprint ins-outs)

;; @@
;; ->
;;; (([&quot;-&quot;] &quot;p&quot; [&quot;-&quot;] &quot;q&quot; [&quot;-&quot;] [&quot;-&quot;])
;;;  ([&quot;-&quot;] &quot;p&quot; (&quot;-&quot; &quot;-&quot;) &quot;q&quot; [&quot;-&quot;] (&quot;-&quot; &quot;-&quot;))
;;;  ((&quot;-&quot; &quot;-&quot;) &quot;p&quot; [&quot;-&quot;] &quot;q&quot; (&quot;-&quot; &quot;-&quot;) [&quot;-&quot;])
;;;  ([&quot;-&quot;] &quot;p&quot; (&quot;-&quot; &quot;-&quot; &quot;-&quot;) &quot;q&quot; [&quot;-&quot;] (&quot;-&quot; &quot;-&quot; &quot;-&quot;))
;;;  ([&quot;-&quot;] &quot;p&quot; (&quot;-&quot; &quot;-&quot; &quot;-&quot; &quot;-&quot;) &quot;q&quot; [&quot;-&quot;] (&quot;-&quot; &quot;-&quot; &quot;-&quot; &quot;-&quot;))
;;;  ((&quot;-&quot; &quot;-&quot;) &quot;p&quot; (&quot;-&quot; &quot;-&quot;) &quot;q&quot; (&quot;-&quot; &quot;-&quot;) (&quot;-&quot; &quot;-&quot;))
;;;  ([&quot;-&quot;] &quot;p&quot; (&quot;-&quot; &quot;-&quot; &quot;-&quot; &quot;-&quot; &quot;-&quot;) &quot;q&quot; [&quot;-&quot;] (&quot;-&quot; &quot;-&quot; &quot;-&quot; &quot;-&quot; &quot;-&quot;))
;;;  ([&quot;-&quot;]
;;;   &quot;p&quot;
;;;   (&quot;-&quot; &quot;-&quot; &quot;-&quot; &quot;-&quot; &quot;-&quot; &quot;-&quot;)
;;;   &quot;q&quot;
;;;   [&quot;-&quot;]
;;;   (&quot;-&quot; &quot;-&quot; &quot;-&quot; &quot;-&quot; &quot;-&quot; &quot;-&quot;))
;;;  ((&quot;-&quot; &quot;-&quot; &quot;-&quot;) &quot;p&quot; [&quot;-&quot;] &quot;q&quot; (&quot;-&quot; &quot;-&quot; &quot;-&quot;) [&quot;-&quot;])
;;;  ((&quot;-&quot; &quot;-&quot;) &quot;p&quot; (&quot;-&quot; &quot;-&quot; &quot;-&quot;) &quot;q&quot; (&quot;-&quot; &quot;-&quot;) (&quot;-&quot; &quot;-&quot; &quot;-&quot;)))
;;; ------
;;; ([[&quot;-&quot;] [&quot;-&quot;] &quot;-p-q--&quot;]
;;;  [[&quot;-&quot;] (&quot;-&quot; &quot;-&quot;) &quot;-p--q---&quot;]
;;;  [(&quot;-&quot; &quot;-&quot;) [&quot;-&quot;] &quot;--p-q---&quot;]
;;;  [[&quot;-&quot;] (&quot;-&quot; &quot;-&quot; &quot;-&quot;) &quot;-p---q----&quot;]
;;;  [[&quot;-&quot;] (&quot;-&quot; &quot;-&quot; &quot;-&quot; &quot;-&quot;) &quot;-p----q-----&quot;]
;;;  [(&quot;-&quot; &quot;-&quot;) (&quot;-&quot; &quot;-&quot;) &quot;--p--q----&quot;]
;;;  [[&quot;-&quot;] (&quot;-&quot; &quot;-&quot; &quot;-&quot; &quot;-&quot; &quot;-&quot;) &quot;-p-----q------&quot;]
;;;  [[&quot;-&quot;] (&quot;-&quot; &quot;-&quot; &quot;-&quot; &quot;-&quot; &quot;-&quot; &quot;-&quot;) &quot;-p------q-------&quot;]
;;;  [(&quot;-&quot; &quot;-&quot; &quot;-&quot;) [&quot;-&quot;] &quot;---p-q----&quot;]
;;;  [(&quot;-&quot; &quot;-&quot;) (&quot;-&quot; &quot;-&quot; &quot;-&quot;) &quot;--p---q-----&quot;])
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; 
;; **
