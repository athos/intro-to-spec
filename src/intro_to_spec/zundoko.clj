(ns intro-to-spec.zundoko
  (:require [clojure.spec :as s]))

(s/def ::zun*4-doko
  (s/cat :1 '#{ずん} :2 '#{ずん} :3 '#{ずん} :4 '#{ずん} :5 '#{どこ}))

(s/def ::has-no-zun*4-doko?
  (fn [xs] (every? #(not (s/valid? ::zun*4-doko %)) (partition 5 1 xs))))

(s/def ::zun-doko-kiyoshi
  (s/cat :preamble   (s/& (s/* '#{ずん どこ}) ::has-no-zun*4-doko?)
         :zun*4-doko ::zun*4-doko
         :kiyoshi    '#{きよし}))
