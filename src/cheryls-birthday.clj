(use 'clojure.core.logic)

(def dates [[:may 15] [:may 16] [:may 19]
            [:june 17] [:june 18]
            [:july 14] [:july 16]
            [:august 14] [:august 15] [:august 17]])

(def months (->> dates (map first) set vec))
(def days (->> dates (map second) set vec))

(run 5 [q]
  (fresh [albert bernard cheryl day month possible_day possible_month]
    (membero cheryl dates)
    (membero possible_day days)
    (membero possible_month months)

    (== albert [month possible_day])
    (== bernard [possible_month day])

    (== cheryl [month day])

    (project [day]
             ()
     )

    ;; 1. albert knows that bernard at first doesn't know
    ;;    -- meaning: bernard cannot guess the bday because it's not a unique day

    (== q [:al albert :ber bernard :cher cheryl])
    ))
