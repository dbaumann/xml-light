# xml-light

## Why
Because I didn't want to bake XML semantics into my project, I wrote a thin wrapper on top of clojure/data.xml to handle the conversion to/from clojure.data.xml.Element.

This enables you to effortlessly convert any data structure to a valid XML string and back into the same form. The resulting structure doesn't fully leverage the expressiveness of XML, but it gets the job done without using XML attributes. Arrays are expressed as repeated tags with the same name, which agrees with the W3C notion of [Bare Vectors](https://www.w3.org/2005/07/xml-schema-patterns.html#Vector).

## Leiningen

```
[xml-light "1.0.0"]
```

## Usage

```clojure
(ns my.ns
  (:require [xml-light.core :as xml-light]))

(def xml-str (xml-light/generate-string {:crunchy "kitten"}))
;; <?xml version="1.0" encoding="UTF-8"?><crunchy>kitten</crunchy>

(def parsed-xml (xml-light/parse-string xml-str))
  ;; {:crunchy "kitten"}
```

## Limitations

Feel free to submit a PR on this README if you find any.

## Inspirations

https://github.com/dakrone/cheshire
https://github.com/ncannasse/xml-light

## License

Copyright © 2017 Dan Baumann

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
