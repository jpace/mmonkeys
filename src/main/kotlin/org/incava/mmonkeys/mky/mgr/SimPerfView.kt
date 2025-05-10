package org.incava.mmonkeys.mky.mgr

import org.incava.mesa.DoubleColumn
import org.incava.mesa.IntColumn
import org.incava.mesa.LongColumn
import org.incava.mesa.StringColumn
import org.incava.mesa.Table
import org.incava.mmonkeys.corpus.Corpus
import java.io.PrintStream
import java.time.Duration
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class SimPerfView(val corpus: Corpus, out: PrintStream = System.out) {
    private val startTime: ZonedDateTime = ZonedDateTime.now()
    private val pattern: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
    private val table: Table
    private var lastWrite: ZonedDateTime? = null

    init {
        table = Table(
            out = out,
            columns = listOf(
                StringColumn("time", 20, leftJustified = false),
                LongColumn("elapsed", 24),
                LongColumn("matches", 12),
                IntColumn("matches %", 12),
                DoubleColumn("matches/sec", 12, 1),
                LongColumn("keystrokes", 12),
                LongColumn("keys/sec", 12),
            )
        )
        table.writeHeader()
    }

    private fun formatTime(dateTime: ZonedDateTime): String = dateTime.format(pattern)

    fun update(matchCount: Int, totalKeystrokes: Long) {
        if (lastWrite == null || lastWrite?.plus(10, ChronoUnit.SECONDS)!! < ZonedDateTime.now()) {
            writeMatch(matchCount, totalKeystrokes)
            lastWrite = ZonedDateTime.now()
        }
    }

    fun writeMatch(matchCount: Int, totalKeystrokes: Long) {
        val now = ZonedDateTime.now()
        val elapsed = Duration.between(startTime, now)
        val pct = 100 * matchCount / corpus.numWords()
        val seconds = elapsed.toSeconds()
        val matchVelocity = if (seconds > 0) matchCount.toDouble() / seconds else -1.0
        val keysVelocity = if (seconds > 0) totalKeystrokes / seconds else -1L
        table.writeRow(formatTime(now), seconds, matchCount, pct, matchVelocity, totalKeystrokes, keysVelocity)
    }
}
