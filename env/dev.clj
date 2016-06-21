(ns dev
  (:require [clojure.repl :refer :all]
            [clojure.pprint :as [pp pprint]]
            [clojure.tools.namespace.repl :refer [refresh]]
            [mount.core :as m]))

(defn go []
  (m/start))

(defn stop []
  (m/stop))

(defn reset []
  (stop)
  (refresh :after 'dev/go))
