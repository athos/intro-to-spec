(ns intro-to-spec.fizzbuzz
  (:require [clojure.spec :as s]))

(s/fdef fizzbuzz
  :args (s/cat :n (s/and integer? #(> % 0)))
  :ret (s/or :int integer? :key keyword?))

(defn fizzbuzz [n]
  (cond (= (mod n 15) 0) :fizzbuzz
        (= (mod n 5)  0) :buzz
        (= (mod n 3)  0) :fizz
        :else            n))
