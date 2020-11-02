(ns timeline-game.runner
  (:require [cljs.test :refer-macros [run-tests]]
            [timeline-game.common_test]))

(run-tests 'timeline-game.common_test)
