;; gorilla-repl.fileformat = 1

;; **
;;; # Logic: The Knights & Knaves, The Hardest Logic Puzzle Ever.
;;; 
;;; <http://www.reddit.com/r/math/comments/23d3se/how_can_we_use_math_to_solve_a_riddle/>
;;; <http://ir.lib.uwo.ca/cgi/viewcontent.cgi?article=2646&context=etd>
;;; <http://en.wikipedia.org/wiki/Knights_and_Knaves>
;; **

;; **
;;; ## intro
;;; 
;;; This reminds me of the Knights & Knaves problem, in which we have two  characters. Knights, who always tell the truth, and Knaves, who always lie. We face a cross road, and wish to know which path is our "correct" path. There is one knight and one knave, but you don't know which is which. How do you figure out which path to take using only one question?
;;; 
;;; What's the secret?
;;; 
;;; Ask either of the characters, "Which path would the other man tell me to take?" Then you take the opposite path.
;;; 
;;; The knight, knowing the knave will lie, will tell you the incorrect path. The knave, knowing the knight will tell the truth, and having to lie about that truth, will tell you the incorrect path. Thus you can negate that, and arrive at the "correct" path.
;;; 
;;; This logic challenge shares a similarity, but it includes a few extra layers of complexity. Now, I'm only a mediocre mathematician, so I can't fully expound on how/if this could be formulated differently, but I am a decent programmer. One way we could formulate this, is as a set of rules and relations of logic (logic is math afaik). Then make use of a logic framework, to arrive at a solution. Some might consider this cheating, but I'm busy so I don't have time to work through this by hand.
;; **

;; @@
(use 'clojure.core.logic)
(require '[clojure.core.logic.pldb :as pldb])
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@

;; Relation to determine where paths lead
(pldb/db-rel paths x y)

;; The person's mind predicate
;; The f stands for function, and nothing else
(pldb/db-rel mind-f x p)

;; Relation defining opposites, so that the knaves
;; know how to lie about their answers
(pldb/db-rel opposite x y)

;; Predicates to represent how knights and knaves answer
;; questions differently.
(defn knightanswer [a b]
  (== a b))
(defn knaveanswer [a b]
  (opposite a b))

(def facts (pldb/db
            [opposite 'Left 'Right]
	        [opposite 'Right 'Left]
	        [opposite 'Death 'Freedom]
	        [opposite 'Freedom 'Death]
	        [opposite 'Jack 'Bill]
	        [opposite 'Bill 'Jack]

			;; Initializing the paths
			;; Left leads to freedom and right leads to death
            [paths 'Left 'Freedom]
            [paths 'Right 'Death]

			;; We will say that Bill is the knight
			;; And Jack is the knave
            [mind-f 'Bill knightanswer]
            [mind-f 'Jack knaveanswer]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;user/facts</span>","value":"#'user/facts"}
;; <=

;; @@
;; Asking a simple question. We will say we're asking it to Jack,
;; and pretend we don't know the answers. This one is the lie.
(pldb/with-db facts
  (doall
     (run* [q]
           (fresh [x p]
                  (paths x 'Freedom)
                  (mind-f 'Jack p)
                  (project [p] (p x q))))))
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-lazy-seq'>(<span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-symbol'>Right</span>","value":"Right"}],"value":"(Right)"}
;; <=

;; @@
;; Bill is telling the truth here
(pldb/with-db facts
  (doall
     (run* [q]
           (fresh [x p]
                  (paths x 'Freedom)
                  (mind-f 'Bill p)
                  (project [p] (p x q))))))
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-lazy-seq'>(<span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-symbol'>Left</span>","value":"Left"}],"value":"(Left)"}
;; <=

;; @@
;; Asking the solution, "If I asked the other guy, what would he say?"
(pldb/with-db facts
  (doall
   (run* [q]
         (fresh [x y p p2 r]
                (paths x 'Freedom)
                (mind-f 'Bill p)
                (mind-f 'Jack p2)
                (project [p p2]
                         (p x y)
                         (p2 y r)
                         (opposite r q))))))
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-lazy-seq'>(<span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-symbol'>Left</span>","value":"Left"}],"value":"(Left)"}
;; <=

;; **
;;; ### TODO
;;; 
;;; How can I structure this problem so that we generate the solution "question" we would ask, rather than just verify our assumption?
;; **

;; **
;;; ## The Hardest Logic Puzzle
;;; 
;;; http://en.wikipedia.org/wiki/The_Hardest_Logic_Puzzle_Ever#The_solution
;;; 
;;; 1. You are faced with three gods (in no particular order,) one of which always lies, one of which always tells the truth, and one of which answers randomly.
;;; 2. You must discover the identity of the three gods by posing up to 3 yes/no questions.
;;; 3. One question can be asked only to one god, though one god may be asked more than one question.
;;; 4. The gods understand english, but answer in an unknown language with "Da" or "Ja" which (in no particular order) mean yes or no.
;;; 
;;; Ok, that's good enough to start defining some facts & relations.
;; **

;; @@
(pldb/db-rel mind-f x y)
(pldb/db-rel opposite x y)
(pldb/db-rel response x y)

(defn honest [a b]
  (== a b))

(defn liar [a b]
  (opposite a b))

(defn random [a b]
  (if (= (rand-nth ['h 't]) 'h)
    (== a b)
    (opposite a b)))

(def hard-facts (pldb/db
            [mind-f 'Alice honest]
            [mind-f 'Bob liar]
            [mind-f 'Eve random]
            
            [response 'Yes 'Da]
            [response 'No 'Ja]
            
            [opposite 'Yes 'No]
            [opposite 'No 'Yes]
            [opposite 'Da 'Ja]
            [opposite 'Ja 'Da]))


;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;user/hard-facts</span>","value":"#'user/hard-facts"}
;; <=

;; **
;;; ## What do we do?? Think of a strategy.
;;; 
;;; What possible ways could we work through a strategy. This could be similar to the knights/knaves.
;;; 
;;; - We could ask 1/3 if one of the others knows something. A higher-order predicate.
;;; - We could try to infer something specific about the person we're asking
;;; 
;;; 1. Possible first questions
;;;   - `Alice`, does `da` mean `yes` iff you are a `liar`, iff `Bob` is `random`?
;;;   - `Alice`, Are an odd number of the following statements `true`: You are a `liar`, `da` means `yes`, `Bob` is `random`?
;;;   - `Alice`, If I asked you a question would you say `ja`?
;;;     - `ja` if the truth is yes
;;;     - `da` if the truth is no
;;; 
;;; Assume that `ja` means `yes` and `da` means `no`.
;;; 
;;; 1. `True` is asked and responds with `ja`. Since he is telling the truth, the truthful answer to Q is `ja`, which means `yes`.
;;; 2. `True` is asked and responds with `da`. Since he is telling the truth, the truthful answer to Q is `da`, which means `no`.
;;; 3. `False` is asked and responds with `ja`. Since he is lying, it follows that if you asked him Q, he would instead answer `da`. He would be lying, so the truthful answer to Q is `ja`, which means `yes`.
;;; 4. `False` is asked and responds with da. Since he is lying, it follows that if you asked him Q, he would in fact answer ja. He would be lying, so the truthful answer to Q is da, which means no.
;;; 
;;; Assume `ja` means `no` and `da` means `yes`.
;;; 
;;; 1. `True` is asked and responds with `ja`. Since he is telling the truth, the truthful answer to Q is `da`, which means `yes`.
;;; 2. `True` is asked and responds with `da`. Since he is telling the truth, the truthful answer to Q is `ja`, which means `no`.
;;; 3. `False` is asked and responds with `ja`. Since he is lying, it follows that if you asked him Q, he would in fact answer `ja`. He would be lying, so the truthful answer to Q is `da`, which means `yes`.
;;; 4. `False` is asked and responds with `da`. Since he is lying, it follows that if you asked him Q, he would instead answer `da`. He would be lying, so the truthful answer to Q is `ja`, which means `no`.
;;; 
;;; ## Questions
;;; 1. Ask `Bob`, "If I asked you 'Is `Alice` `Random`?', would you say `ja`?". If `Bob` answers `ja`, either `Bob` is `Random` (and is answering randomly), or `Bob` is not `Random` and the answer indicates that `Alice` is indeed `Random`. Either way, `Eve` is not `Random`. If `Bob` answers `da`, either `Bob` is `Random` (and is answering randomly), or `Bob` is not `Random` and the answer indicates that `Alice` is not `Random`. Either way, you know the identity of a god who is not `Random`.
;;; 2. Go to the god who was identified as not being `Random` by the previous question (either `Alice` or `Eve`), and ask him: "If I asked you 'Are you `False`?', would you say `ja`?". Since he is not `Random`, an answer of `da` indicates that he is `True` and an answer of `ja` indicates that he is `False`.
;;; 3. Ask the same god the question: "If I asked you 'Is `Bob` `Random`?', would you say `ja`?". If the answer is `ja`, `Bob` is `Random`; if the answer is `da`, the god you have not yet spoken to is `Random`. The remaining god can be identified by elimination.
;; **

;; @@
(pldb/with-db hard-facts
  (doall
	(run* [q z]
          (fresh [p p2 x y]
          (mind-f 'Bob p)
          (mind-f 'Alice p2)
          (project [p p2 z]
                   (p x y)
                   (p2 y z)
                   (p z q))))))

;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-lazy-seq'>(<span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[<span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-symbol'>No</span>","value":"No"},{"type":"html","content":"<span class='clj-symbol'>Yes</span>","value":"Yes"}],"value":"[No Yes]"},{"type":"list-like","open":"<span class='clj-vector'>[<span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-symbol'>Ja</span>","value":"Ja"},{"type":"html","content":"<span class='clj-symbol'>Da</span>","value":"Da"}],"value":"[Ja Da]"},{"type":"list-like","open":"<span class='clj-vector'>[<span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-symbol'>Da</span>","value":"Da"},{"type":"html","content":"<span class='clj-symbol'>Ja</span>","value":"Ja"}],"value":"[Da Ja]"},{"type":"list-like","open":"<span class='clj-vector'>[<span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-symbol'>Yes</span>","value":"Yes"},{"type":"html","content":"<span class='clj-symbol'>No</span>","value":"No"}],"value":"[Yes No]"}],"value":"([No Yes] [Ja Da] [Da Ja] [Yes No])"}
;; <=

;; @@
;; TODO
;; @@

;; @@

;; @@
