(ns dev
  (:require [clojure.repl :refer :all]
            [clojure.pprint :refer [pp pprint]]
            [clojure.tools.namespace.repl :refer [refresh]]
            [mount.core :as m]
            [intro-to-spec.queue :as q]
            [clojure.spec :as s]
            [clojure.spec.gen :as gen]
            [clojure.spec.test :as t]))

(defn go []
  (s/instrument-all)
  (m/start))

(defn stop []
  (m/stop))

(defn reset []
  (stop)
  (reset! @#'s/instrumented-vars {})
  (refresh :after 'dev/go))
