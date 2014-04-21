;; gorilla-repl.fileformat = 1

;; **
;;; # Some Logic games done using core.logic & for comprehension
;;; 
;;; http://blog.jenkster.com/2013/02/solving-logic-puzzles-with-clojures-corelogic.html
;;; http://blog.jenkster.com/2013/02/solving-logic-puzzles-with-clojures-core-logic-part-two.html
;;; 
;;; 1. Of Landon and Jason, one has the 7:30pm reservation and the other loves mozzarella.
;;; 2. The blue cheese enthusiast subscribed to Fortune.
;;; 3. The muenster enthusiast didn't subscribe to Vogue.
;;; 4. The 5 people were the Fortune subscriber, Landon, the person with a reservation at 5:00pm, the mascarpone enthusiast, and the Vogue subscriber.
;;; 5. The person with a reservation at 5:00pm didn't subscribe to Time.
;;; 6. The Cosmopolitan subscriber has an earlier reservation than the mascarpone enthusiast.
;;; 7. Bailey has a later reservation than the blue cheese enthusiast.
;;; 8. Either the person with a reservation at 7:00pm or the person with a reservation at 7:30pm subscribed to Fortune.
;;; 9. Landon has a later reservation than the Time subscriber.
;;; 10. The Fortune subscriber is not Jamari.
;;; 11. The person with a reservation at 5:00pm loves mozzarella.
;; **

;; @@
(require '[clojure.tools.macro :refer [symbol-macrolet]])
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(defn rule-2
  "The blue cheese enthusiast subscribed to Fortune."
  [answers]
  (membero [(lvar) :fortune :blue-cheese (lvar)] answers))

(defn rule-11
  "The person with a reservation at 5:00pm loves mozzarella."
  [answers]
  (symbol-macrolet [_ (lvar)]
                   (membero [_ _ :mozzarella 5] answers)))

(defne not-membero [x l]
  ([_ []])
  ([_ [?y . ?r]]
    (!= x ?y)
    (not-membero x ?r)))

(defn BAD-rule-3
  "The muenster enthusiast didn't subscribe to Vogue."
  [answers]
  (symbol-macrolet
   [_ (lvar)]
   (not-membero (== [_ :vogue :muenster _] answers))))

(defn rule-3
  "The muenster enthusiast didn't subscribe to Vogue."
  [answers]
  (symbol-macrolet
   [_ (lvar)]
   (fresh [s1 s2]
          (== [_ :vogue _ _] s1)
          (== [_ _ :muenster _] s2)
          (membero s1 answers)
          (membero s2 answers)
          (!= s1 s2))))

(defn rule-5
  "The person with a reservation at 5:00pm didn't subscribe to Time."
  [answers]
  (symbol-macrolet
   [_ (lvar)]
   (fresh [s1 s2]
          (== [_ _ _ 5] s1)
          (== [_ :time _ _] s2)
          (membero s1 answers)
          (membero s2 answers)
          (!= s1 s2))))

(defn rule-10
  "The Fortune subscriber is not Jamari."
  [answers]
  (symbol-macrolet
   [_ (lvar)]
   (fresh [s1 s2]
          (== [_ :fortune _ _] s1)
          (== [:jamari _ _ _] s2)
          (membero s1 answers)
          (membero s2 answers)
          (!= s1 s2))))

(defn rule-8
  "Either the person with a reservation at 7:00pm or the person with a
   reservation at 7:30pm subscribed to Fortune."
  [answers]
  (symbol-macrolet
   [_ (lvar)]
   (fresh [r]
          (membero [_ :fortune _ r] answers)
          (conde [(== r 7)]
                 [(== r 7.5)]))))

(defn rule-1
  "Of Landon and Jason, one has the 7:30pm reservation and the other loves mozzarella."
  [answers]
  (symbol-macrolet
   [_ (lvar)]
   (fresh [c1 r1 c2 r2]
          (membero [:landon _ c1 r1] answers)
          (membero [:jason _ c2 r2] answers)
          (conde
           [(== r1 7.5) (== c2 :mozzarella)]
           [(== r2 7.5) (== c1 :mozzarella)]))))

(defn rule-4
  "The 5 people were the Fortune subscriber, Landon, the person with a
   reservation at 5:00pm, the mascarpone enthusiast, and the Vogue
   subscriber."
  [answers]
  (symbol-macrolet
   [_ (lvar)]
   (permuteo [[_ :fortune _ _]
              [:landon _ _ _]
              [_ _ _ 5]
              [_ _ :mascarpone _]
              [_ :vogue _ _]]
             answers)))

(defne lefto
  "x appears to the left of y in collection l."
  [x y l]
  ([_ _ [x . tail]] (membero y tail))
  ([_ _ [_ . tail]] (lefto x y tail)))

(defn rule-6
  "The Cosmopolitan subscriber has an earlier reservation than the
   mascarpone enthusiast."
  [answers]
  (symbol-macrolet
   [_ (lvar)]
   (fresh [r1 r2]
          (membero [_ :cosmopolitan _ r1] answers)
          (membero [_ _ :mascarpone r2] answers)
          (lefto r1 r2 [5 6 7 7.5 8.5]))))

(defn rule-7
  "Bailey has a later reservation than the blue cheese enthusiast."
  [answers]
  (symbol-macrolet
   [_ (lvar)]
   (fresh [r1 r2]
          (membero [_ _ :blue-cheese r1] answers)
          (membero [:bailey _ _ r2] answers)
          (lefto r1 r2 [5 6 7 7.5 8.5]))))

(defn rule-9
  "Landon has a later reservation than the Time subscriber."
  [answers]
  (symbol-macrolet
   [_ (lvar)]
   (fresh [r1 r2]
          (membero [_ :time _ r1] answers)
          (membero [:landon _ _ r2] answers)
          (lefto r1 r2 [5 6 7 7.5 8.5]))))

(let [people       (repeatedly 5 lvar)
      magazines    (repeatedly 5 lvar)
      cheeses      (repeatedly 5 lvar)
      reservations (repeatedly 5 lvar)
      answers (map list people magazines cheeses reservations)]
  (run 1 [q]
       (== q answers)
       (== people [:amaya :bailey :jamari :jason :landon])
       (rule-1 answers)
       (rule-2 answers)
       (rule-3 answers)
       (rule-4 answers)
       (rule-5 answers)
       (rule-6 answers)
       (rule-7 answers)
       (rule-8 answers)
       (rule-9 answers)
       (rule-10 answers)
       (rule-11 answers)
       (permuteo magazines [:fortune :time :cosmopolitan :us-weekly :vogue])
       (permuteo cheeses [:asiago :blue-cheese :mascarpone :mozzarella :muenster])
       (permuteo reservations [5 6 7 7.5 8.5])))
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-lazy-seq'>(<span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-lazy-seq'>(<span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-lazy-seq'>(<span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:amaya</span>","value":":amaya"},{"type":"html","content":"<span class='clj-keyword'>:fortune</span>","value":":fortune"},{"type":"html","content":"<span class='clj-keyword'>:blue-cheese</span>","value":":blue-cheese"},{"type":"html","content":"<span class='clj-long'>7</span>","value":"7"}],"value":"(:amaya :fortune :blue-cheese 7)"},{"type":"list-like","open":"<span class='clj-lazy-seq'>(<span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:bailey</span>","value":":bailey"},{"type":"html","content":"<span class='clj-keyword'>:vogue</span>","value":":vogue"},{"type":"html","content":"<span class='clj-keyword'>:asiago</span>","value":":asiago"},{"type":"html","content":"<span class='clj-double'>8.5</span>","value":"8.5"}],"value":"(:bailey :vogue :asiago 8.5)"},{"type":"list-like","open":"<span class='clj-lazy-seq'>(<span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:jamari</span>","value":":jamari"},{"type":"html","content":"<span class='clj-keyword'>:time</span>","value":":time"},{"type":"html","content":"<span class='clj-keyword'>:mascarpone</span>","value":":mascarpone"},{"type":"html","content":"<span class='clj-long'>6</span>","value":"6"}],"value":"(:jamari :time :mascarpone 6)"},{"type":"list-like","open":"<span class='clj-lazy-seq'>(<span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:jason</span>","value":":jason"},{"type":"html","content":"<span class='clj-keyword'>:cosmopolitan</span>","value":":cosmopolitan"},{"type":"html","content":"<span class='clj-keyword'>:mozzarella</span>","value":":mozzarella"},{"type":"html","content":"<span class='clj-long'>5</span>","value":"5"}],"value":"(:jason :cosmopolitan :mozzarella 5)"},{"type":"list-like","open":"<span class='clj-lazy-seq'>(<span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:landon</span>","value":":landon"},{"type":"html","content":"<span class='clj-keyword'>:us-weekly</span>","value":":us-weekly"},{"type":"html","content":"<span class='clj-keyword'>:muenster</span>","value":":muenster"},{"type":"html","content":"<span class='clj-double'>7.5</span>","value":"7.5"}],"value":"(:landon :us-weekly :muenster 7.5)"}],"value":"((:amaya :fortune :blue-cheese 7) (:bailey :vogue :asiago 8.5) (:jamari :time :mascarpone 6) (:jason :cosmopolitan :mozzarella 5) (:landon :us-weekly :muenster 7.5))"}],"value":"(((:amaya :fortune :blue-cheese 7) (:bailey :vogue :asiago 8.5) (:jamari :time :mascarpone 6) (:jason :cosmopolitan :mozzarella 5) (:landon :us-weekly :muenster 7.5)))"}
;; <=

;; **
;;; ## logic programming sucks
;;; 
;;; http://programming-puzzler.blogspot.com/2013/03/logic-programming-is-overrated.html
;; **

;; @@
(require '[clojure.math.combinatorics :refer [permutations]])
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(defn solve-logic-puzzle []
  (let [people [:amaya :bailey :jamari :jason :landon]]
    (first    
      (for [[fortune time cosmopolitan us-weekly vogue] (permutations people) ; magazines
            [asiago blue-cheese mascarpone mozzarella muenster] (permutations people) ; cheeses
            ; We bind the reservations in two steps, so we have a name for the overall order
            reservations (permutations people)
            :let [[five six seven seven-thirty eight-thirty] reservations]
 
; THE CONSTRAINTS IN PLAIN ENGLISH            
;        Of Landon and Jason, one has the 7:30pm reservation and the other loves mozzarella.
;        The blue cheese enthusiast subscribed to Fortune.
;        The muenster enthusiast didn't subscribe to Vogue.
;        The 5 people were the Fortune subscriber, Landon, the person with a reservation at 5:00pm, the mascarpone enthusiast, and the Vogue subscriber.
;        The person with a reservation at 5:00pm didn't subscribe to Time.
;        The Cosmopolitan subscriber has an earlier reservation than the mascarpone enthusiast.
;        Bailey has a later reservation than the blue cheese enthusiast.
;        Either the person with a reservation at 7:00pm or the person with a reservation at 7:30pm subscribed to Fortune.
;        Landon has a later reservation than the Time subscriber.
;        The Fortune subscriber is not Jamari.
;        The person with a reservation at 5:00pm loves mozzarella.
 
; THE CONSTRAINTS IN CLOJURE (in the same order)
            :when (= (set [seven-thirty mozzarella]) (set [:landon :jason]))
            :when (= blue-cheese fortune)            
            :when (not= muenster vogue)
            :when (= (count (set [fortune :landon five mascarpone vogue])) 5)
            :when (not= five time)
            :when (< (.indexOf reservations cosmopolitan) (.indexOf reservations mascarpone))
            :when (> (.indexOf reservations :bailey) (.indexOf reservations blue-cheese))
            :when (#{seven seven-thirty} fortune)
            :when (> (.indexOf reservations :landon) (.indexOf reservations time))
            :when (not= fortune :jamari)
            :when (= five mozzarella)]
 
; RETURN THE ANSWER        
        (array-map
          :fortune fortune :time time :cosmopolitan cosmopolitan :us-weekly us-weekly :vogue vogue
          :asiago asiago :blue-cheese blue-cheese :mascarpone mascarpone :mozzarella mozzarella :muenster muenster
          :five five :six six :seven seven :seven-thirty seven-thirty :eight-thirty eight-thirty)))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;user/solve-logic-puzzle</span>","value":"#'user/solve-logic-puzzle"}
;; <=

;; @@
(solve-logic-puzzle)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-map'>{<span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:fortune</span>","value":":fortune"},{"type":"html","content":"<span class='clj-keyword'>:amaya</span>","value":":amaya"}],"value":"[:fortune :amaya]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:time</span>","value":":time"},{"type":"html","content":"<span class='clj-keyword'>:jamari</span>","value":":jamari"}],"value":"[:time :jamari]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:cosmopolitan</span>","value":":cosmopolitan"},{"type":"html","content":"<span class='clj-keyword'>:jason</span>","value":":jason"}],"value":"[:cosmopolitan :jason]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:us-weekly</span>","value":":us-weekly"},{"type":"html","content":"<span class='clj-keyword'>:landon</span>","value":":landon"}],"value":"[:us-weekly :landon]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:vogue</span>","value":":vogue"},{"type":"html","content":"<span class='clj-keyword'>:bailey</span>","value":":bailey"}],"value":"[:vogue :bailey]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:asiago</span>","value":":asiago"},{"type":"html","content":"<span class='clj-keyword'>:bailey</span>","value":":bailey"}],"value":"[:asiago :bailey]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:blue-cheese</span>","value":":blue-cheese"},{"type":"html","content":"<span class='clj-keyword'>:amaya</span>","value":":amaya"}],"value":"[:blue-cheese :amaya]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:mascarpone</span>","value":":mascarpone"},{"type":"html","content":"<span class='clj-keyword'>:jamari</span>","value":":jamari"}],"value":"[:mascarpone :jamari]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:mozzarella</span>","value":":mozzarella"},{"type":"html","content":"<span class='clj-keyword'>:jason</span>","value":":jason"}],"value":"[:mozzarella :jason]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:muenster</span>","value":":muenster"},{"type":"html","content":"<span class='clj-keyword'>:landon</span>","value":":landon"}],"value":"[:muenster :landon]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:five</span>","value":":five"},{"type":"html","content":"<span class='clj-keyword'>:jason</span>","value":":jason"}],"value":"[:five :jason]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:six</span>","value":":six"},{"type":"html","content":"<span class='clj-keyword'>:jamari</span>","value":":jamari"}],"value":"[:six :jamari]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:seven</span>","value":":seven"},{"type":"html","content":"<span class='clj-keyword'>:amaya</span>","value":":amaya"}],"value":"[:seven :amaya]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:seven-thirty</span>","value":":seven-thirty"},{"type":"html","content":"<span class='clj-keyword'>:landon</span>","value":":landon"}],"value":"[:seven-thirty :landon]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:eight-thirty</span>","value":":eight-thirty"},{"type":"html","content":"<span class='clj-keyword'>:bailey</span>","value":":bailey"}],"value":"[:eight-thirty :bailey]"}],"value":"{:fortune :amaya, :time :jamari, :cosmopolitan :jason, :us-weekly :landon, :vogue :bailey, :asiago :bailey, :blue-cheese :amaya, :mascarpone :jamari, :mozzarella :jason, :muenster :landon, :five :jason, :six :jamari, :seven :amaya, :seven-thirty :landon, :eight-thirty :bailey}"}
;; <=

;; @@
(defn logic-puzzle []
  (let [people [:amaya :bailey :jamari :jason :landon]]
    (for [[fortune time cosmopolitan us-weekly vogue] (permutations people) ; magazines
          :when (not= fortune :jamari)
          
          [asiago blue-cheese mascarpone mozzarella muenster] (permutations people) ; cheeses
          :when (= blue-cheese fortune)
          :when (not= muenster vogue)
          
          reservations (permutations people)
          :let [[five six seven seven-thirty eight-thirty] reservations]
          
          :when (not= five time)
          :when (= five mozzarella)            
          :when (#{seven seven-thirty} fortune)
          :when (= (set [seven-thirty mozzarella]) (set [:landon :jason]))
          :when (= (count (set [fortune :landon five mascarpone vogue])) 5)
          :when (< (.indexOf reservations cosmopolitan) (.indexOf reservations mascarpone))
          :when (> (.indexOf reservations :bailey) (.indexOf reservations blue-cheese))
          :when (> (.indexOf reservations :landon) (.indexOf reservations time))]
      
      (array-map
        :fortune fortune :time time :cosmopolitan cosmopolitan :us-weekly us-weekly :vogue vogue
        :asiago asiago :blue-cheese blue-cheese :mascarpone mascarpone :mozzarella mozzarella :muenster muenster
        :five five :six six :seven seven :seven-thirty seven-thirty :eight-thirty eight-thirty))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;user/logic-puzzle</span>","value":"#'user/logic-puzzle"}
;; <=

;; @@
(logic-puzzle)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-lazy-seq'>(<span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-map'>{<span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:fortune</span>","value":":fortune"},{"type":"html","content":"<span class='clj-keyword'>:amaya</span>","value":":amaya"}],"value":"[:fortune :amaya]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:time</span>","value":":time"},{"type":"html","content":"<span class='clj-keyword'>:jamari</span>","value":":jamari"}],"value":"[:time :jamari]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:cosmopolitan</span>","value":":cosmopolitan"},{"type":"html","content":"<span class='clj-keyword'>:jason</span>","value":":jason"}],"value":"[:cosmopolitan :jason]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:us-weekly</span>","value":":us-weekly"},{"type":"html","content":"<span class='clj-keyword'>:landon</span>","value":":landon"}],"value":"[:us-weekly :landon]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:vogue</span>","value":":vogue"},{"type":"html","content":"<span class='clj-keyword'>:bailey</span>","value":":bailey"}],"value":"[:vogue :bailey]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:asiago</span>","value":":asiago"},{"type":"html","content":"<span class='clj-keyword'>:bailey</span>","value":":bailey"}],"value":"[:asiago :bailey]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:blue-cheese</span>","value":":blue-cheese"},{"type":"html","content":"<span class='clj-keyword'>:amaya</span>","value":":amaya"}],"value":"[:blue-cheese :amaya]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:mascarpone</span>","value":":mascarpone"},{"type":"html","content":"<span class='clj-keyword'>:jamari</span>","value":":jamari"}],"value":"[:mascarpone :jamari]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:mozzarella</span>","value":":mozzarella"},{"type":"html","content":"<span class='clj-keyword'>:jason</span>","value":":jason"}],"value":"[:mozzarella :jason]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:muenster</span>","value":":muenster"},{"type":"html","content":"<span class='clj-keyword'>:landon</span>","value":":landon"}],"value":"[:muenster :landon]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:five</span>","value":":five"},{"type":"html","content":"<span class='clj-keyword'>:jason</span>","value":":jason"}],"value":"[:five :jason]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:six</span>","value":":six"},{"type":"html","content":"<span class='clj-keyword'>:jamari</span>","value":":jamari"}],"value":"[:six :jamari]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:seven</span>","value":":seven"},{"type":"html","content":"<span class='clj-keyword'>:amaya</span>","value":":amaya"}],"value":"[:seven :amaya]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:seven-thirty</span>","value":":seven-thirty"},{"type":"html","content":"<span class='clj-keyword'>:landon</span>","value":":landon"}],"value":"[:seven-thirty :landon]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:eight-thirty</span>","value":":eight-thirty"},{"type":"html","content":"<span class='clj-keyword'>:bailey</span>","value":":bailey"}],"value":"[:eight-thirty :bailey]"}],"value":"{:fortune :amaya, :time :jamari, :cosmopolitan :jason, :us-weekly :landon, :vogue :bailey, :asiago :bailey, :blue-cheese :amaya, :mascarpone :jamari, :mozzarella :jason, :muenster :landon, :five :jason, :six :jamari, :seven :amaya, :seven-thirty :landon, :eight-thirty :bailey}"}],"value":"({:fortune :amaya, :time :jamari, :cosmopolitan :jason, :us-weekly :landon, :vogue :bailey, :asiago :bailey, :blue-cheese :amaya, :mascarpone :jamari, :mozzarella :jason, :muenster :landon, :five :jason, :six :jamari, :seven :amaya, :seven-thirty :landon, :eight-thirty :bailey})"}
;; <=

;; **
;;;     (((:amaya :fortune :blue-cheese 7) 
;;;       (:bailey :vogue :asiago 8.5) 
;;;       (:jamari :time :mascarpone 6) 
;;;       (:jason :cosmopolitan :mozzarella 5) 
;;;       (:landon :us-weekly :muenster 7.5)))
;;;       
;;;     ({:fortune :amaya, 
;;;       :time :jamari, 
;;;       :cosmopolitan :jason, 
;;;       :us-weekly :landon, 
;;;       :vogue :bailey, 
;;;       :asiago :bailey, 
;;;       :blue-cheese :amaya, 
;;;       :mascarpone :jamari, 
;;;       :mozzarella :jason, 
;;;       :muenster :landon, 
;;;       :five :jason, 
;;;       :six :jamari, 
;;;       :seven :amaya, 
;;;       :seven-thirty :landon, 
;;;       :eight-thirty :bailey})
;; **

;; **
;;; http://swannodette.github.io/2013/03/09/logic-programming-is-underrated/
;; **

;; @@

;; @@
