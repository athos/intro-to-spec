(ns intro-to-spec.brainfuck
  (:require [clojure.spec :as s]))

(s/def ::brainf*ck
  (s/* (s/& (s/alt :insn (set "><+-.,")
                   :loop (s/& (s/cat :start #{\[}
                                     :body  ::brainf*ck
                                     :end   #{\]})
                              (s/conformer #(:body %))))
            (s/conformer second))))

(defn step [{:keys [ptr mem] :as state} insn]
  (case insn
    \> (update state :ptr inc)
    \< (update state :ptr dec)
    \+ (update-in state [:mem ptr] (fnil inc 0))
    \- (update-in state [:mem ptr] (fnil dec 0))
    \. (do (print (char (get mem ptr)))
           state)
    \, (assoc-in state [:mem ptr] (.read *in*))
    (->> (iterate #(reduce step % insn) state)
         (some #(and (= (get (:mem %) (:ptr %)) 0) %)))))

(defn run [code]
  (let [insns (s/conform ::brainf*ck (seq code))]
    (if (= insns ::s/invalid)
      insns
      (reduce step {:ptr 0 :mem {}} insns))))
