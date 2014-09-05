;; gorilla-repl.fileformat = 1

;; **
;;; # Permutations & Craps
;; **

;; @@
(ns craps
  (:refer-clojure :exclude [== + * - /])
  (:require [gorilla-plot.core :as plot])
  (:use clojure.core.matrix
        clojure.core.matrix.operators))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(defn vec-remove
  "remove elem in coll"
  [coll pos]
  (vec (concat (subvec coll 0 pos) (subvec coll (inc pos)))))

(vec-remove [1 2 3] 0)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"}],"value":"[2 3]"}
;; <=

;; @@
(defn deck
  "Generates a random permutation of n-decks of cards"
  [n]
  (let [total (* 52 n)
        cards (->> total range (map inc) vec)]
    (loop [shuffled nil
           deck cards]
      (let [i (rand-int (count deck))]
        (if (= (count deck) 1)
          (conj shuffled (nth deck i))
	      (recur (conj shuffled (nth deck i))
                 (vec-remove deck i)))))))

(println "Assert: sum(deck) == (1+52)*26," (= (* 53 26) (apply + (deck 1))))

;; Shuffle 2 decks (104 cards)
(deck 2)
;; @@
;; ->
;;; Assert: sum(deck) == (1+52)*26, true
;;; 
;; <-
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>11</span>","value":"11"},{"type":"html","content":"<span class='clj-long'>22</span>","value":"22"},{"type":"html","content":"<span class='clj-long'>28</span>","value":"28"},{"type":"html","content":"<span class='clj-long'>34</span>","value":"34"},{"type":"html","content":"<span class='clj-long'>63</span>","value":"63"},{"type":"html","content":"<span class='clj-long'>86</span>","value":"86"},{"type":"html","content":"<span class='clj-long'>7</span>","value":"7"},{"type":"html","content":"<span class='clj-long'>54</span>","value":"54"},{"type":"html","content":"<span class='clj-long'>59</span>","value":"59"},{"type":"html","content":"<span class='clj-long'>82</span>","value":"82"},{"type":"html","content":"<span class='clj-long'>15</span>","value":"15"},{"type":"html","content":"<span class='clj-long'>29</span>","value":"29"},{"type":"html","content":"<span class='clj-long'>37</span>","value":"37"},{"type":"html","content":"<span class='clj-long'>48</span>","value":"48"},{"type":"html","content":"<span class='clj-long'>74</span>","value":"74"},{"type":"html","content":"<span class='clj-long'>92</span>","value":"92"},{"type":"html","content":"<span class='clj-long'>33</span>","value":"33"},{"type":"html","content":"<span class='clj-long'>102</span>","value":"102"},{"type":"html","content":"<span class='clj-long'>58</span>","value":"58"},{"type":"html","content":"<span class='clj-long'>90</span>","value":"90"},{"type":"html","content":"<span class='clj-long'>81</span>","value":"81"},{"type":"html","content":"<span class='clj-long'>98</span>","value":"98"},{"type":"html","content":"<span class='clj-long'>101</span>","value":"101"},{"type":"html","content":"<span class='clj-long'>100</span>","value":"100"},{"type":"html","content":"<span class='clj-long'>9</span>","value":"9"},{"type":"html","content":"<span class='clj-long'>62</span>","value":"62"},{"type":"html","content":"<span class='clj-long'>30</span>","value":"30"},{"type":"html","content":"<span class='clj-long'>10</span>","value":"10"},{"type":"html","content":"<span class='clj-long'>38</span>","value":"38"},{"type":"html","content":"<span class='clj-long'>27</span>","value":"27"},{"type":"html","content":"<span class='clj-long'>41</span>","value":"41"},{"type":"html","content":"<span class='clj-long'>43</span>","value":"43"},{"type":"html","content":"<span class='clj-long'>55</span>","value":"55"},{"type":"html","content":"<span class='clj-long'>18</span>","value":"18"},{"type":"html","content":"<span class='clj-long'>75</span>","value":"75"},{"type":"html","content":"<span class='clj-long'>83</span>","value":"83"},{"type":"html","content":"<span class='clj-long'>68</span>","value":"68"},{"type":"html","content":"<span class='clj-long'>94</span>","value":"94"},{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"},{"type":"html","content":"<span class='clj-long'>51</span>","value":"51"},{"type":"html","content":"<span class='clj-long'>6</span>","value":"6"},{"type":"html","content":"<span class='clj-long'>8</span>","value":"8"},{"type":"html","content":"<span class='clj-long'>71</span>","value":"71"},{"type":"html","content":"<span class='clj-long'>52</span>","value":"52"},{"type":"html","content":"<span class='clj-long'>93</span>","value":"93"},{"type":"html","content":"<span class='clj-long'>64</span>","value":"64"},{"type":"html","content":"<span class='clj-long'>26</span>","value":"26"},{"type":"html","content":"<span class='clj-long'>104</span>","value":"104"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>56</span>","value":"56"},{"type":"html","content":"<span class='clj-long'>87</span>","value":"87"},{"type":"html","content":"<span class='clj-long'>35</span>","value":"35"},{"type":"html","content":"<span class='clj-long'>78</span>","value":"78"},{"type":"html","content":"<span class='clj-long'>85</span>","value":"85"},{"type":"html","content":"<span class='clj-long'>72</span>","value":"72"},{"type":"html","content":"<span class='clj-long'>17</span>","value":"17"},{"type":"html","content":"<span class='clj-long'>16</span>","value":"16"},{"type":"html","content":"<span class='clj-long'>42</span>","value":"42"},{"type":"html","content":"<span class='clj-long'>96</span>","value":"96"},{"type":"html","content":"<span class='clj-long'>88</span>","value":"88"},{"type":"html","content":"<span class='clj-long'>103</span>","value":"103"},{"type":"html","content":"<span class='clj-long'>49</span>","value":"49"},{"type":"html","content":"<span class='clj-long'>39</span>","value":"39"},{"type":"html","content":"<span class='clj-long'>36</span>","value":"36"},{"type":"html","content":"<span class='clj-long'>69</span>","value":"69"},{"type":"html","content":"<span class='clj-long'>60</span>","value":"60"},{"type":"html","content":"<span class='clj-long'>19</span>","value":"19"},{"type":"html","content":"<span class='clj-long'>5</span>","value":"5"},{"type":"html","content":"<span class='clj-long'>77</span>","value":"77"},{"type":"html","content":"<span class='clj-long'>14</span>","value":"14"},{"type":"html","content":"<span class='clj-long'>32</span>","value":"32"},{"type":"html","content":"<span class='clj-long'>99</span>","value":"99"},{"type":"html","content":"<span class='clj-long'>4</span>","value":"4"},{"type":"html","content":"<span class='clj-long'>13</span>","value":"13"},{"type":"html","content":"<span class='clj-long'>20</span>","value":"20"},{"type":"html","content":"<span class='clj-long'>84</span>","value":"84"},{"type":"html","content":"<span class='clj-long'>80</span>","value":"80"},{"type":"html","content":"<span class='clj-long'>70</span>","value":"70"},{"type":"html","content":"<span class='clj-long'>79</span>","value":"79"},{"type":"html","content":"<span class='clj-long'>21</span>","value":"21"},{"type":"html","content":"<span class='clj-long'>61</span>","value":"61"},{"type":"html","content":"<span class='clj-long'>95</span>","value":"95"},{"type":"html","content":"<span class='clj-long'>65</span>","value":"65"},{"type":"html","content":"<span class='clj-long'>47</span>","value":"47"},{"type":"html","content":"<span class='clj-long'>67</span>","value":"67"},{"type":"html","content":"<span class='clj-long'>25</span>","value":"25"},{"type":"html","content":"<span class='clj-long'>46</span>","value":"46"},{"type":"html","content":"<span class='clj-long'>44</span>","value":"44"},{"type":"html","content":"<span class='clj-long'>50</span>","value":"50"},{"type":"html","content":"<span class='clj-long'>66</span>","value":"66"},{"type":"html","content":"<span class='clj-long'>91</span>","value":"91"},{"type":"html","content":"<span class='clj-long'>12</span>","value":"12"},{"type":"html","content":"<span class='clj-long'>40</span>","value":"40"},{"type":"html","content":"<span class='clj-long'>45</span>","value":"45"},{"type":"html","content":"<span class='clj-long'>97</span>","value":"97"},{"type":"html","content":"<span class='clj-long'>73</span>","value":"73"},{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"html","content":"<span class='clj-long'>57</span>","value":"57"},{"type":"html","content":"<span class='clj-long'>89</span>","value":"89"},{"type":"html","content":"<span class='clj-long'>53</span>","value":"53"},{"type":"html","content":"<span class='clj-long'>31</span>","value":"31"},{"type":"html","content":"<span class='clj-long'>24</span>","value":"24"},{"type":"html","content":"<span class='clj-long'>23</span>","value":"23"},{"type":"html","content":"<span class='clj-long'>76</span>","value":"76"}],"value":"(11 22 28 34 63 86 7 54 59 82 15 29 37 48 74 92 33 102 58 90 81 98 101 100 9 62 30 10 38 27 41 43 55 18 75 83 68 94 3 51 6 8 71 52 93 64 26 104 1 56 87 35 78 85 72 17 16 42 96 88 103 49 39 36 69 60 19 5 77 14 32 99 4 13 20 84 80 70 79 21 61 95 65 47 67 25 46 44 50 66 91 12 40 45 97 73 2 57 89 53 31 24 23 76)"}
;; <=

;; @@
(def n 1000000)

(defn uniform [a b] (fn [] (+ a (rand-nth (range b)))))

(def die (uniform 1 6))

(defn roll 
  "Sum of 2 random dice"
  [] (+ (die) (die)))

;; Test out some expectations of uniform variables to ensure accuracy

(let [e (/ (apply + (take n (repeatedly die))) n)]
  (println "Assert: E[Unif(1,6)] ≈ 3.5,"
           (float e)
           (< 3.4 e 3.6)))
(let [e (/ (apply + (take n (repeatedly #(+ (die) (die))))) n)]
  (println "Assert: E[Unif(1,6) + Unif(1,6)] ≈ 7,"
           (float e)
           (< 6.9 e 7.1)))
;; @@
;; ->
;;; Assert: E[Unif(1,6)] ≈ 3.5, 3.499192 true
;;; Assert: E[Unif(1,6) + Unif(1,6)] ≈ 7, 6.999008 true
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(defn avg [coll]
  (/ (apply + coll)
     (count coll)))

(defn std-err [mu X]
  (let [n (count X)]
    (/ (distance X mu) ;; Euclidean distance, built into core.matrix
       n)))

(defn craps-game 
  "Returns the number of rolls in a simulated game of craps"
  []
  (let [initial-roll (roll)]
    (loop [roll-count 1
           current-roll initial-roll
           initial initial-roll
           first-roll true]
      (cond
       (or (and (some #{current-roll} [2 3 7 11 12]) first-roll)
           (and (= current-roll initial) (not first-roll))
           (= current-roll 7)) roll-count
       :else (recur (inc roll-count)
                    (roll)
                    initial
                    false)))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;craps/craps-game</span>","value":"#'craps/craps-game"}
;; <=

;; @@
(def m 10000)
(def sample (take m (repeatedly craps-game)))

(println "--- max" (apply max sample))
(println "--- avg" (float (avg sample)))

(plot/histogram sample :bins 30)
;; @@
;; ->
;;; --- max 30
;;; --- avg 3.4168
;;; 
;; <-
;; =>
;;; {"type":"vega","content":{"axes":[{"scale":"x","type":"x"},{"scale":"y","type":"y"}],"scales":[{"name":"x","type":"linear","range":"width","zero":false,"domain":{"data":"a05b7a33-2d27-444a-8499-35fdc82cfffc","field":"data.x"}},{"name":"y","type":"linear","range":"height","nice":true,"zero":false,"domain":{"data":"a05b7a33-2d27-444a-8499-35fdc82cfffc","field":"data.y"}}],"marks":[{"type":"line","from":{"data":"a05b7a33-2d27-444a-8499-35fdc82cfffc"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"y":{"scale":"y","field":"data.y"},"interpolate":{"value":"step-before"},"fill":{"value":"steelblue"},"fillOpacity":{"value":0.4},"stroke":{"value":"steelblue"},"strokeWidth":{"value":2},"strokeOpacity":{"value":1}}}}],"data":[{"name":"a05b7a33-2d27-444a-8499-35fdc82cfffc","values":[{"x":1.0,"y":0},{"x":1.9666666666666668,"y":3395.0},{"x":2.9333333333333336,"y":1795.0},{"x":3.9000000000000004,"y":1296.0},{"x":4.866666666666667,"y":988.0},{"x":5.833333333333334,"y":686.0},{"x":6.800000000000001,"y":506.0},{"x":7.7666666666666675,"y":378.0},{"x":8.733333333333334,"y":244.0},{"x":9.700000000000001,"y":183.0},{"x":10.666666666666668,"y":151.0},{"x":11.633333333333335,"y":102.0},{"x":12.600000000000001,"y":87.0},{"x":13.566666666666668,"y":52.0},{"x":14.533333333333335,"y":32.0},{"x":15.500000000000002,"y":26.0},{"x":16.46666666666667,"y":24.0},{"x":17.433333333333337,"y":11.0},{"x":18.400000000000006,"y":14.0},{"x":19.366666666666674,"y":14.0},{"x":20.333333333333343,"y":7.0},{"x":21.30000000000001,"y":3.0},{"x":22.26666666666668,"y":0.0},{"x":23.23333333333335,"y":3.0},{"x":24.200000000000017,"y":0.0},{"x":25.166666666666686,"y":1.0},{"x":26.133333333333354,"y":1.0},{"x":27.100000000000023,"y":0.0},{"x":28.06666666666669,"y":0.0},{"x":29.03333333333336,"y":0.0},{"x":30.00000000000003,"y":1.0},{"x":30.966666666666697,"y":0}]}],"width":400,"height":247.2187957763672,"padding":{"bottom":20,"top":10,"right":10,"left":50}},"value":"#gorilla_repl.vega.VegaView{:content {:axes [{:scale \"x\", :type \"x\"} {:scale \"y\", :type \"y\"}], :scales [{:name \"x\", :type \"linear\", :range \"width\", :zero false, :domain {:data \"a05b7a33-2d27-444a-8499-35fdc82cfffc\", :field \"data.x\"}} {:name \"y\", :type \"linear\", :range \"height\", :nice true, :zero false, :domain {:data \"a05b7a33-2d27-444a-8499-35fdc82cfffc\", :field \"data.y\"}}], :marks [{:type \"line\", :from {:data \"a05b7a33-2d27-444a-8499-35fdc82cfffc\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :y {:scale \"y\", :field \"data.y\"}, :interpolate {:value \"step-before\"}, :fill {:value \"steelblue\"}, :fillOpacity {:value 0.4}, :stroke {:value \"steelblue\"}, :strokeWidth {:value 2}, :strokeOpacity {:value 1}}}}], :data [{:name \"a05b7a33-2d27-444a-8499-35fdc82cfffc\", :values ({:x 1.0, :y 0} {:x 1.9666666666666668, :y 3395.0} {:x 2.9333333333333336, :y 1795.0} {:x 3.9000000000000004, :y 1296.0} {:x 4.866666666666667, :y 988.0} {:x 5.833333333333334, :y 686.0} {:x 6.800000000000001, :y 506.0} {:x 7.7666666666666675, :y 378.0} {:x 8.733333333333334, :y 244.0} {:x 9.700000000000001, :y 183.0} {:x 10.666666666666668, :y 151.0} {:x 11.633333333333335, :y 102.0} {:x 12.600000000000001, :y 87.0} {:x 13.566666666666668, :y 52.0} {:x 14.533333333333335, :y 32.0} {:x 15.500000000000002, :y 26.0} {:x 16.46666666666667, :y 24.0} {:x 17.433333333333337, :y 11.0} {:x 18.400000000000006, :y 14.0} {:x 19.366666666666674, :y 14.0} {:x 20.333333333333343, :y 7.0} {:x 21.30000000000001, :y 3.0} {:x 22.26666666666668, :y 0.0} {:x 23.23333333333335, :y 3.0} {:x 24.200000000000017, :y 0.0} {:x 25.166666666666686, :y 1.0} {:x 26.133333333333354, :y 1.0} {:x 27.100000000000023, :y 0.0} {:x 28.06666666666669, :y 0.0} {:x 29.03333333333336, :y 0.0} {:x 30.00000000000003, :y 1.0} {:x 30.966666666666697, :y 0})}], :width 400, :height 247.2188, :padding {:bottom 20, :top 10, :right 10, :left 50}}}"}
;; <=

;; @@
(defn craps-simulation
  "Simulates the expected number of rolls needed in a game of craps"
  [n]
  (let [rolls (take n (repeatedly craps-game))
        expected (avg rolls)]
    {:expected (float expected)
     :std-err (std-err expected rolls)}))

(craps-simulation n)
;; @@
;; ->
;;; expected: 3.373526
;;; 
;; <-
;; =>
;;; {"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:expected</span>","value":":expected"},{"type":"html","content":"<span class='clj-unkown'>3.373403</span>","value":"3.373403"}],"value":"[:expected 3.373403]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:std-err</span>","value":":std-err"},{"type":"html","content":"<span class='clj-double'>0.003007833638948637</span>","value":"0.003007833638948637"}],"value":"[:std-err 0.003007833638948637]"}],"value":"{:expected 3.373403, :std-err 0.003007833638948637}"}
;; <=
