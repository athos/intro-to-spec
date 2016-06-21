(ns intro-to-spec.core
  (:refer-clojure :exclude [empty empty? remove])
  (:require [clojure.core :as c]
            [clojure.spec :as s]))

(s/def ::queue
  (s/tuple (s/nilable seq?) (s/nilable seq?)))

(s/def ::empty-queue
  (s/tuple nil? nil?))

(s/def ::non-empty-queue
  (s/and ::queue #(not (s/valid? ::empty-queue %))))

(s/fdef empty
  :args (s/cat)
  :ret ::queue)
(defn empty [] [nil nil])

(s/fdef add
  :args (s/cat :q ::queue
               :x ::s/any)
  :ret ::queue)
(defn add [[f b] x]
  [f (cons x b)])

(s/fdef empty?
  :args (s/cat :q ::queue)
  :ret boolean?)
(defn empty? [[f b]]
  (c/empty? f))

(s/fdef front
  :args (s/cat :q ::non-empty-queue)
  :ret ::s/any)
(defn front [[f b]]
  (first f))

(s/fdef remove
  :args (s/cat :q ::non-empty-queue)
  :ret ::queue)
(defn remove [[f b]]
  (if-let [f' (rest f)]
    [f' b]
    [(reverse b) nil]))

