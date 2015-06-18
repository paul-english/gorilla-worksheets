;; gorilla-repl.fileformat = 1

;; **
;;; # Polyas Urn
;;; 
;; **

;; @@
(ns grieving-dove
  (:require [gorilla-plot.core :as plot]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(defn proportion [urn]
  (let [total (apply + urn)]
    (/ (first urn) total)))

(defn pick-random [urn]
  (if (<= (rand) (proportion urn))
    0 1))

(defn add-ball [color urn]
  (let [n (nth urn color)]
    (assoc urn color (inc n))))

(defn polyas-urn [urn]
  (-> (pick-random urn)
      (add-ball urn)))

(defn simulate 
  "General tool for running a simulation function and returning the last result"
  [f x n]
  (-> (iterate f x)
      (nth n)))

(defn urn-1000 
  "Returns the proportion of red balls after 1000 iterations"
  []
  (let [urn (simulate polyas-urn [1 1] 1000)]
    (proportion urn)))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;grieving-dove/urn-1000</span>","value":"#'grieving-dove/urn-1000"}
;; <=

;; @@
(plot/histogram
  (take 2000 (repeatedly urn-1000))
  :bins 20)
;; @@
;; =>
;;; {"type":"vega","content":{"axes":[{"scale":"x","type":"x"},{"scale":"y","type":"y"}],"scales":[{"name":"x","type":"linear","range":"width","zero":false,"domain":{"data":"e2b86e91-e310-41eb-89da-7c40f556db33","field":"data.x"}},{"name":"y","type":"linear","range":"height","nice":true,"zero":false,"domain":{"data":"e2b86e91-e310-41eb-89da-7c40f556db33","field":"data.y"}}],"marks":[{"type":"line","from":{"data":"e2b86e91-e310-41eb-89da-7c40f556db33"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"y":{"scale":"y","field":"data.y"},"interpolate":{"value":"step-before"},"fill":{"value":"steelblue"},"fillOpacity":{"value":0.4},"stroke":{"value":"steelblue"},"strokeWidth":{"value":2},"strokeOpacity":{"value":1}}}}],"data":[{"name":"e2b86e91-e310-41eb-89da-7c40f556db33","values":[{"x":9.980039920159667E-4,"y":0},{"x":0.05089820359281437,"y":112.0},{"x":0.10079840319361277,"y":95.0},{"x":0.15069860279441116,"y":102.0},{"x":0.20059880239520955,"y":103.0},{"x":0.25049900199600794,"y":104.0},{"x":0.30039920159680633,"y":103.0},{"x":0.3502994011976047,"y":89.0},{"x":0.4001996007984031,"y":102.0},{"x":0.4500998003992015,"y":110.0},{"x":0.4999999999999999,"y":105.0},{"x":0.5499001996007983,"y":86.0},{"x":0.5998003992015967,"y":99.0},{"x":0.6497005988023951,"y":95.0},{"x":0.6996007984031934,"y":94.0},{"x":0.7495009980039918,"y":102.0},{"x":0.7994011976047902,"y":77.0},{"x":0.8493013972055886,"y":98.0},{"x":0.899201596806387,"y":100.0},{"x":0.9491017964071854,"y":110.0},{"x":0.9990019960079838,"y":113.0},{"x":1.0489021956087823,"y":1.0},{"x":1.0988023952095807,"y":0}]}],"width":400,"height":247.2187957763672,"padding":{"bottom":20,"top":10,"right":10,"left":50}},"value":"#gorilla_repl.vega.VegaView{:content {:axes [{:scale \"x\", :type \"x\"} {:scale \"y\", :type \"y\"}], :scales [{:name \"x\", :type \"linear\", :range \"width\", :zero false, :domain {:data \"e2b86e91-e310-41eb-89da-7c40f556db33\", :field \"data.x\"}} {:name \"y\", :type \"linear\", :range \"height\", :nice true, :zero false, :domain {:data \"e2b86e91-e310-41eb-89da-7c40f556db33\", :field \"data.y\"}}], :marks [{:type \"line\", :from {:data \"e2b86e91-e310-41eb-89da-7c40f556db33\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :y {:scale \"y\", :field \"data.y\"}, :interpolate {:value \"step-before\"}, :fill {:value \"steelblue\"}, :fillOpacity {:value 0.4}, :stroke {:value \"steelblue\"}, :strokeWidth {:value 2}, :strokeOpacity {:value 1}}}}], :data [{:name \"e2b86e91-e310-41eb-89da-7c40f556db33\", :values ({:x 9.980039920159667E-4, :y 0} {:x 0.05089820359281437, :y 112.0} {:x 0.10079840319361277, :y 95.0} {:x 0.15069860279441116, :y 102.0} {:x 0.20059880239520955, :y 103.0} {:x 0.25049900199600794, :y 104.0} {:x 0.30039920159680633, :y 103.0} {:x 0.3502994011976047, :y 89.0} {:x 0.4001996007984031, :y 102.0} {:x 0.4500998003992015, :y 110.0} {:x 0.4999999999999999, :y 105.0} {:x 0.5499001996007983, :y 86.0} {:x 0.5998003992015967, :y 99.0} {:x 0.6497005988023951, :y 95.0} {:x 0.6996007984031934, :y 94.0} {:x 0.7495009980039918, :y 102.0} {:x 0.7994011976047902, :y 77.0} {:x 0.8493013972055886, :y 98.0} {:x 0.899201596806387, :y 100.0} {:x 0.9491017964071854, :y 110.0} {:x 0.9990019960079838, :y 113.0} {:x 1.0489021956087823, :y 1.0} {:x 1.0988023952095807, :y 0})}], :width 400, :height 247.2188, :padding {:bottom 20, :top 10, :right 10, :left 50}}}"}
;; <=

;; @@
(defn urn-compare-1000-2000 []
  (let [m-998 (simulate polyas-urn [1 1] 1000)
        m-1998 (simulate polyas-urn m-998 1000)]
    (->> [m-998 m-1998]
         (map proportion)
         (apply /))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;grieving-dove/urn-compare-1000-2000</span>","value":"#'grieving-dove/urn-compare-1000-2000"}
;; <=

;; @@
(def comparisons (take 100 (repeatedly urn-compare-1000-2000)))

(plot/histogram comparisons :bins 30)
;; @@
;; =>
;;; {"type":"vega","content":{"axes":[{"scale":"x","type":"x"},{"scale":"y","type":"y"}],"scales":[{"name":"x","type":"linear","range":"width","zero":false,"domain":{"data":"f5cbcbba-f50e-43ce-a9bf-7f82ebaaba19","field":"data.x"}},{"name":"y","type":"linear","range":"height","nice":true,"zero":false,"domain":{"data":"f5cbcbba-f50e-43ce-a9bf-7f82ebaaba19","field":"data.y"}}],"marks":[{"type":"line","from":{"data":"f5cbcbba-f50e-43ce-a9bf-7f82ebaaba19"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"y":{"scale":"y","field":"data.y"},"interpolate":{"value":"step-before"},"fill":{"value":"steelblue"},"fillOpacity":{"value":0.4},"stroke":{"value":"steelblue"},"strokeWidth":{"value":2},"strokeOpacity":{"value":1}}}}],"data":[{"name":"f5cbcbba-f50e-43ce-a9bf-7f82ebaaba19","values":[{"x":0.605455755156354,"y":0},{"x":0.6222739705773638,"y":1.0},{"x":0.6390921859983736,"y":0.0},{"x":0.6559104014193834,"y":0.0},{"x":0.6727286168403932,"y":0.0},{"x":0.689546832261403,"y":0.0},{"x":0.7063650476824128,"y":0.0},{"x":0.7231832631034226,"y":0.0},{"x":0.7400014785244324,"y":0.0},{"x":0.7568196939454422,"y":0.0},{"x":0.773637909366452,"y":0.0},{"x":0.7904561247874619,"y":0.0},{"x":0.8072743402084717,"y":0.0},{"x":0.8240925556294815,"y":0.0},{"x":0.8409107710504913,"y":0.0},{"x":0.8577289864715011,"y":0.0},{"x":0.8745472018925109,"y":0.0},{"x":0.8913654173135207,"y":2.0},{"x":0.9081836327345305,"y":0.0},{"x":0.9250018481555403,"y":1.0},{"x":0.9418200635765501,"y":1.0},{"x":0.9586382789975599,"y":4.0},{"x":0.9754564944185697,"y":9.0},{"x":0.9922747098395795,"y":21.0},{"x":1.0090929252605894,"y":32.0},{"x":1.0259111406815993,"y":13.0},{"x":1.0427293561026092,"y":9.0},{"x":1.0595475715236191,"y":2.0},{"x":1.076365786944629,"y":2.0},{"x":1.093184002365639,"y":2.0},{"x":1.1100022177866489,"y":0.0},{"x":1.1268204332076588,"y":1.0},{"x":1.1436386486286687,"y":0}]}],"width":400,"height":247.2187957763672,"padding":{"bottom":20,"top":10,"right":10,"left":50}},"value":"#gorilla_repl.vega.VegaView{:content {:axes [{:scale \"x\", :type \"x\"} {:scale \"y\", :type \"y\"}], :scales [{:name \"x\", :type \"linear\", :range \"width\", :zero false, :domain {:data \"f5cbcbba-f50e-43ce-a9bf-7f82ebaaba19\", :field \"data.x\"}} {:name \"y\", :type \"linear\", :range \"height\", :nice true, :zero false, :domain {:data \"f5cbcbba-f50e-43ce-a9bf-7f82ebaaba19\", :field \"data.y\"}}], :marks [{:type \"line\", :from {:data \"f5cbcbba-f50e-43ce-a9bf-7f82ebaaba19\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :y {:scale \"y\", :field \"data.y\"}, :interpolate {:value \"step-before\"}, :fill {:value \"steelblue\"}, :fillOpacity {:value 0.4}, :stroke {:value \"steelblue\"}, :strokeWidth {:value 2}, :strokeOpacity {:value 1}}}}], :data [{:name \"f5cbcbba-f50e-43ce-a9bf-7f82ebaaba19\", :values ({:x 0.605455755156354, :y 0} {:x 0.6222739705773638, :y 1.0} {:x 0.6390921859983736, :y 0.0} {:x 0.6559104014193834, :y 0.0} {:x 0.6727286168403932, :y 0.0} {:x 0.689546832261403, :y 0.0} {:x 0.7063650476824128, :y 0.0} {:x 0.7231832631034226, :y 0.0} {:x 0.7400014785244324, :y 0.0} {:x 0.7568196939454422, :y 0.0} {:x 0.773637909366452, :y 0.0} {:x 0.7904561247874619, :y 0.0} {:x 0.8072743402084717, :y 0.0} {:x 0.8240925556294815, :y 0.0} {:x 0.8409107710504913, :y 0.0} {:x 0.8577289864715011, :y 0.0} {:x 0.8745472018925109, :y 0.0} {:x 0.8913654173135207, :y 2.0} {:x 0.9081836327345305, :y 0.0} {:x 0.9250018481555403, :y 1.0} {:x 0.9418200635765501, :y 1.0} {:x 0.9586382789975599, :y 4.0} {:x 0.9754564944185697, :y 9.0} {:x 0.9922747098395795, :y 21.0} {:x 1.0090929252605894, :y 32.0} {:x 1.0259111406815993, :y 13.0} {:x 1.0427293561026092, :y 9.0} {:x 1.0595475715236191, :y 2.0} {:x 1.076365786944629, :y 2.0} {:x 1.093184002365639, :y 2.0} {:x 1.1100022177866489, :y 0.0} {:x 1.1268204332076588, :y 1.0} {:x 1.1436386486286687, :y 0})}], :width 400, :height 247.2188, :padding {:bottom 20, :top 10, :right 10, :left 50}}}"}
;; <=

;; @@

;; @@
