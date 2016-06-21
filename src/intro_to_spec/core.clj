(ns intro-to-spec.core
  (:refer-clojure :exclude [empty empty? remove])
  (:require [clojure.core :as c]
            [clojure.spec :as s]))

(defn empty [] [nil nil])

(defn add [[f b] x]
  [f (cons x b)])

(defn empty? [[f b]]
  (c/empty? f))

(defn front [[f b]]
  (first f))

(defn remove [[f b]]
  (if-let [f' (rest f)]
    [f' b]
    [(reverse b) nil]))

