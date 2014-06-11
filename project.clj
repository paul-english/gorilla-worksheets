(defproject gorilla-worksheets "0.1.0-SNAPSHOT"
  :description "TODO"
  :url "TODO"
  :license {:name "TODO: Choose a license"
            :url "http://choosealicense.com/"}
  :dependencies [[aysylu/loom "0.4.2"]
                 [cheshire "5.3.1"]
                 [clj-auto-diff "0.1.0"]
                 [clj-time "0.7.0"]
                 [com.google.guava/guava "15.0"]
                 [com.redbrainlabs/system-graph "0.1.0"]
                 [com.stuartsierra/component "0.2.1"]
                 [incanter/incanter-charts "1.5.4"]
                 [incanter/incanter-core "1.5.4"]
                 [instaparse "1.3.2"]
                 [liberator "0.11.0"]
                 [log4j/log4j "1.2.17"]
                 [net.mikera/core.matrix "0.22.0"]
                 [org.apfloat/apfloat "1.6.3"]
                 [org.clojure/clojure "1.6.0"]
                 [org.clojure/core.async "0.1.278.0-76b25b-alpha"]
                 [org.clojure/core.logic "0.8.7"]
                 [org.clojure/math.combinatorics "0.0.7"]
                 [org.clojure/tools.logging "0.2.6"]
                 [org.slf4j/slf4j-log4j12 "1.7.7"]]
  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.4"]]
                   :plugins [[lein-pdo "0.1.1"]
                             [lein-gorilla "0.2.0"]]
                   :source-paths ["dev"]}})
