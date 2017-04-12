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
          <xs:choice minOccurs=\"1\" maxOccurs=\"unbounded\">
            <xs:element name=\"name\" type=\"xs:string\" minOccurs=\"1\" maxOccurs=\"1\"/>
            <xs:element name=\"accounts\" type=\"xs:string\" minOccurs=\"2\" maxOccurs=\"2\"/>
          </xs:choice>
        </xs:sequence>
      </xs:complexType>
    </xs:element>
    </xs:schema>")

(def is-valid-xml?
  (xmlv/create-validation-fn (java.io.StringReader. fixture-xml-schema)))

(deftest test-generate-string
  (testing "produces valid xml"
    (is (not (instance? clj_xml_validation.core.ValidationFailureResult
                        (is-valid-xml? (generate-string fixture)))))))

(deftest test-parse-string
  (testing "parses output xml into the same structure"
    (let [output-xml (generate-string fixture)]
      (is (= (parse-string output-xml) fixture)))))
