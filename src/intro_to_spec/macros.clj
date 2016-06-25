(ns intro-to-spec.macros
  (:refer-clojure :exclude [with-open])
  (:require [clojure.spec :as s]))

(s/def ::binding (s/cat :name simple-symbol? :init ::s/any))

(s/def ::bindings (s/and (s/* ::binding) vector?))

(s/fdef with-open
  :args (s/cat :bindings ::bindings
               :body (s/* ::s/any))
  :ret ::s/any)
(defmacro with-open [bindings & body]
  (let [[binding & more] (s/conform ::bindings bindings)]
    (if-not binding
      `(do ~@body)
      `(let ~[(:name binding) (:init binding)]
         (try
           (with-open ~(vec (s/unform ::bindings more))
             ~@body)
           (finally
             (.close ~(:name binding))))))))
