(ns intro-to-spec.fizzbuzz-test
  (:require [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check.properties :as prop]
            [clojure.spec :as s]
            [intro-to-spec.fizzbuzz :as fb]))

(defspec fizzbuzz-prop
  (prop/for-all [n (s/gen (s/and integer? #(> % 0)))]
    (let [ret (fb/fizzbuzz n)]
      (cond (= (mod n 3) 0) (contains? #{:fizz :fizzbuzz} ret)
            (= (mod n 5) 0) (contains? #{:buzz :fizzbuzz} ret)
            :else (= n ret)))))
