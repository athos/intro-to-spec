(defproject intro-to-spec "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha7"]]
  :profiles {:dev {:source-paths ["env"]
                   :dependencies [[org.clojure/tools.namespace "0.3.0-alpha3"]
                                  [mount "0.1.10"]]}})
