(ns xml-light.core-test
  (:require [clojure.test :refer :all]
            [xml-light.core :refer :all]
            [clj-xml-validation.core :as xmlv]))

(def fixture
  {:envelope {:name "john"
              :accounts ["43567" "85631"]}})

(def fixture-xml-schema
  "<?xml version=\"1.0\"?>
    <xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">
    <xs:element name=\"envelope\">
      <xs:complexType>
        <xs:sequence>
          <xs:element name=\"name\" type=\"xs:string\" minOccurs=\"0\" maxOccurs=\"1\"/>
          <xs:element name=\"accounts\" type=\"xs:string\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>
        </xs:sequence>
      </xs:complexType>
    </xs:element>
    </xs:schema>")

(def is-valid-xml?
  (xmlv/create-validation-fn (java.io.StringReader. fixture-xml-schema)))

(deftest test-generate-string
  (testing "produces valid xml"
    (is (is-valid-xml? (generate-string fixture)))))

(deftest test-parse-string
  (testing "parses output xml into the same structure"
    (let [output-xml (generate-string fixture)]
      (is (= (parse-string output-xml) fixture)))))
