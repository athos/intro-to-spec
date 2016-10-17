(ns intro-to-spec.macros
  (:refer-clojure :exclude [with-open condp])
  (:require [clojure.spec :as s]))

(s/def ::binding (s/cat :name simple-symbol? :init any?))

(s/def ::bindings (s/and (s/* ::binding) vector?))

(s/def ::with-open
  (s/cat :bindings ::bindings :body (s/* any?)))

(s/fdef with-open
  :args ::with-open
  :ret any?)
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

(s/def ::condp-clause
  (s/alt :binary (s/cat :test-expr any?
                        :result-expr (s/and any? (complement #{:>>})))
         :ternary (s/cat :test-expr any?
                         :>> #{:>>}
                         :result-fn any?)))

(s/def ::condp-clauses+default
  (s/cat :clauses (s/* ::condp-clause) :default (s/? any?)))

(s/fdef condp
  :args (s/cat :pred any? :expr any?
               :clauses+default ::condp-clauses+default))

(defmacro condp [pred expr & clauses]
  (let [gpred (gensym "pred__")
        gexpr (gensym "expr__")
        {:keys [clauses default]} (s/conform ::condp-clauses+default clauses)
        emit (fn emit [pred expr clauses default]
               (if (empty? clauses)
                 (or default
                     `(throw (IllegalArgumentException.
                              (str "No matching clause: " ~expr))))
                 (let [[[case clause] & clauses] clauses]
                   (if (= case :binary)
                     `(if (~pred ~(:test-expr clause) ~expr)
                        ~(:result-expr clause)
                        ~(emit pred expr clauses default))
                     `(if-let [p# (~pred ~(:test-expr clause) ~expr)]
                        (~(:result-fn clause) p#)
                        ~(emit pred expr clauses default))))))]
    `(let [~gpred ~pred
           ~gexpr ~expr]
       ~(emit gpred gexpr clauses default))))
