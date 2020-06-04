(ns timeline-game.common_test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [timeline-game.common :refer [ordered?]]))

(deftest test-ordered?
  (testing "Order doesnt matter for same years"
    (let [cards {1 {:year 2020} 2 {:year 2020}}]
      (is (= true
             (ordered? [1 2] cards)
             (ordered? [2 1] cards)))))
  (testing "Correct order"
    (is (= true
           (ordered? [] {})))
    (let [cards {1 {:year -1500} 2 {:year -1200}}]
      (is (= true
             (ordered? [1 2] cards))))
    (let [cards {1 {:year -1500} 2 {:year -1200} 3 {:year 2050}}]
      (is (= true
             (ordered? [1 2 3] cards)))))
  (testing "Incorrect order"
    (let [cards {1 {:year -1500} 2 {:year -1200}}]
      (is (= false
             (ordered? [2 1] cards))))
    (let [cards {1 {:year -1500} 2 {:year -1200} 3 {:year 2050}}]
      (is (= false
             (ordered? [1 3 2] cards))))))
