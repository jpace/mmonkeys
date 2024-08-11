package org.incava.mmonkeys.util

import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

open class SimClock {
    val startTime = ZonedDateTime.of(0, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC"))
    private val pattern = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")

    open fun writeLine(secondsFromStart: Long, msg: String) {
        val simTime = startTime.plusSeconds(secondsFromStart)
        val formatted = simTime.format(pattern)
        println("<id> - $formatted - $msg")
    }
}

class SimClock2 : SimClock() {
    private var prevYear: Int = -1
    private var prevMonth: Int = -1
    private var prevDay: Int = -1

    override fun writeLine(secondsFromStart: Long, msg: String) {
        val simTime = startTime.plusSeconds(secondsFromStart)
        var pattern = ""
        if (simTime.year == prevYear) {
            pattern += "    "
            if (simTime.monthValue == prevMonth) {
                pattern += "   "
                pattern += if (simTime.dayOfMonth == prevDay) "   " else "/dd"
            } else {
                pattern += "/MM/dd"
            }
        } else {
            pattern += "yyyy/MM/dd"
        }
        prevYear = simTime.year
        prevMonth = simTime.monthValue
        prevDay = simTime.dayOfMonth
        pattern += " HH:mm:ss"
        val dtf = DateTimeFormatter.ofPattern(pattern)
        val formatted = simTime.format(dtf)
        println("<id> - $formatted - $msg")
    }
}