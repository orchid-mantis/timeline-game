(ns timeline-game.common-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [timeline-game.common :refer [put-before ordered?]]))

(deftest test-put-before
  (testing "Place item on start"
    (let [items [1 2 3 4]]
      (is (= [5 1 2 3 4]
             (put-before items 0 5)))))
  (testing "Put item in middle"
    (let [items [1 2 3 4]]
      (is (= [1 2 5 3 4]
             (put-before items 2 5)))))
  (testing "Put item at end"
    (let [items [1 2 3 4]]
      (is (= [1 2 3 4 5]
             (put-before items 4 5))))))

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
