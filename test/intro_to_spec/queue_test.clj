(ns intro-to-spec.queue-test
  (:require [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check.properties :as prop]
            [clojure.spec :as s]
            [intro-to-spec.queue :as q]))

(defn retrieve [[f b]]
  (concat f (reverse b)))

(defn equiv [q q']
  (= (retrieve q) (retrieve q')))

(defspec prop-empty?
  (prop/for-all [q (s/gen ::q/queue)]
    (= (q/empty? q) (equiv q (q/empty)))))

(defspec prop-front-empty
  (prop/for-all [x (s/gen any?)]
    (= (q/front (q/add (q/empty) x)) x)))

(defspec prop-front-add
  (prop/for-all [q (s/gen ::q/non-empty-queue)
                 x (s/gen any?)]
    (= (q/front (q/add q x)) (q/front q))))

(defspec prop-remove-empty
  (prop/for-all [x (s/gen any?)]
    (equiv (q/remove (q/add (q/empty) x))
           (q/empty))))

(defspec prop-remove-add
  (prop/for-all [q (s/gen ::q/non-empty-queue)
                 x (s/gen any?)]
    (equiv (q/remove (q/add q x)) (q/add (q/remove q) x))))
