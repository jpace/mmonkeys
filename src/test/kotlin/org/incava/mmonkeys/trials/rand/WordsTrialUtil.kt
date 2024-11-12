package org.incava.mmonkeys.trials.rand

import org.incava.ikdk.io.Console
import java.time.ZonedDateTime
import java.util.*

object WordsTrialUtil {
    fun showCurrent(numMatched: Int, longerMatched: Int, matches: SortedMap<Int, Int>) {
        if (numMatched > 0 && numMatched % 1000 == 0) {
            Console.info("all", numMatched)
            Console.info("longer", longerMatched)
            Console.info("total", matches.values.sum())
            Console.info("matches", matches)
        }
    }
}