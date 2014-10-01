(ns brainiac.test.plugins.wmata-train-tracker
  (:use [brainiac.plugins.wmata-train-tracker]
        [clojure.test]
        [clojure.contrib.mock]))

(def sample-json (java.io.ByteArrayInputStream. (.getBytes "{\"Trains\":[{\"Car\":\"6\",\"Destination\":\"SilvrSpg\",\"DestinationCode\":\"B08\",\"DestinationName\":\"Silver Spring\",\"Group\":\"1\",\"Line\":\"RD\",\"LocationCode\":\"A06\",\"LocationName\":\"Van Ness-UDC\",\"Min\":\"BRD\"},{\"Car\":\"6\",\"Destination\":\"Shady Gr\",\"DestinationCode\":\"A15\",\"DestinationName\":\"Shady Grove\",\"Group\":\"2\",\"Line\":\"RD\",\"LocationCode\":\"A06\",\"LocationName\":\"Van Ness-UDC\",\"Min\":\"1\"},{\"Car\":\"6\",\"Destination\":\"Grsvnor\",\"DestinationCode\":\"A11\",\"DestinationName\":\"Grosvenor-Strathmore\",\"Group\":\"2\",\"Line\":\"RD\",\"LocationCode\":\"A06\",\"LocationName\":\"Van Ness-UDC\",\"Min\":\"5\"},{\"Car\":\"8\",\"Destination\":\"Glenmont\",\"DestinationCode\":\"B11\",\"DestinationName\":\"Glenmont\",\"Group\":\"1\",\"Line\":\"RD\",\"LocationCode\":\"A06\",\"LocationName\":\"Van Ness-UDC\",\"Min\":\"8\"},{\"Car\":\"8\",\"Destination\":\"Shady Gr\",\"DestinationCode\":\"A15\",\"DestinationName\":\"Shady Grove\",\"Group\":\"2\",\"Line\":\"RD\",\"LocationCode\":\"A06\",\"LocationName\":\"Van Ness-UDC\",\"Min\":\"10\"},{\"Car\":\"6\",\"Destination\":\"SilvrSpg\",\"DestinationCode\":\"B08\",\"DestinationName\":\"Silver Spring\",\"Group\":\"1\",\"Line\":\"RD\",\"LocationCode\":\"A06\",\"LocationName\":\"Van Ness-UDC\",\"Min\":\"12\"}]}")))

(deftest test-transform
  (expect [now (returns 1338310719000)]
    (let [parse-result (transform sample-json)]
        (testing "sets name"
          (is (="wmata-train-tracker" (:name parse-result))))

        (testing "sets data"
          (is (= [{:DestinationName "Silver Spring" :line "RD" :MIN "BRD"}
                  {:destination "54th/Cermak" :line "Pink" :arrival-time "1 MIN" :due-in-millis 89000}
                  {:destination "Forest Park" :line "Blue" :arrival-time "1 MIN" :due-in-millis 92000}
                  {:destination "Cottage Grove" :line "Green" :arrival-time "2 MIN" :due-in-millis 129000}
                  ] (:data parse-result)))))))
