(ns xml-light.core
  (:require
   [clojure.data.xml :as xml]))

(defn generate-string [ds]
  "serializes a data structure as xml"
  (letfn
   [(to-xml [v]
      (cond
        (map? v)
        (reduce
         (fn [z i]
           (let [tagName (key i) tagValue (val i)]
             (cons
              (cond
                (vector? tagValue)
                (for [x tagValue] (xml/element tagName {} (to-xml x)))
                :else
                (xml/element tagName {} (to-xml tagValue)))
              z)))
         [] v)
        :else
        v))]
    (xml/emit-str (to-xml ds))))

(defn parse-string [str]
  "produces a data structure from an xml string"
  (letfn
   [(xml-leaf? [v]
      (and
       (seq? v)
       (= (count v) 1)
       (not (instance? clojure.data.xml.Element (first v)))))
    (from-xml [v]
              (cond
                (xml-leaf? v)
                (first v)
                (instance? clojure.data.xml.Element v)
                {(:tag v) (from-xml (:content v))}
                (seq? v)
                (let [elements (for [x v] (from-xml x))
                      element-groups (partition-by (fn [x] (first (keys x))) elements)]
                  (apply merge
                         (for [group element-groups]
                           (let [group-name (first (keys (first group)))]
                             (cond
                               (= (count group) 1)
                               (first group)
                               :else
                               {group-name (for [v group] (get v group-name))})))))))]
    (from-xml (xml/parse (java.io.StringReader. str)))))
