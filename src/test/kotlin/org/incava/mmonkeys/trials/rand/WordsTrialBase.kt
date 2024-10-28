package org.incava.mmonkeys.trials.rand

import org.incava.ikdk.io.Console
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object WordsTrialBase {
    val pattern = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")

    fun showCurrent(numMatched: Int, longerMatched: Int, matches: SortedMap<Int, Int>) {
        if (numMatched > 0 && numMatched % 1000 == 0) {
            val now = ZonedDateTime.now()
            println(now.format(pattern))
            Console.printf("all: %,d", numMatched)
            Console.printf("longer: %,d", longerMatched)
            Console.printf("total: %,d", matches.values.sum())
            println(matches)
        }
    }
}