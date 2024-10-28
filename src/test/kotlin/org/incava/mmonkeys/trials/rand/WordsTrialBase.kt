package org.incava.mmonkeys.trials.rand

import org.incava.ikdk.io.Console
import java.util.*

object WordsTrialBase {
    fun showCurrent(numMatched: Int, longerMatched: Int, matches: SortedMap<Int, Int>) {
        if (numMatched > 0 && numMatched % 1000 == 0) {
            Console.printf("all: %,d", numMatched)
            Console.printf("longer: %,d", longerMatched)
            Console.printf("total: %,d", matches.values.sum())
            println(matches)
        }
    }
}