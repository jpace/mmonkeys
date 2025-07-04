package org.incava.mmonkeys.mky.mgr

import org.incava.mesa.IntColumn
import org.incava.mesa.LongColumn
import org.incava.mesa.StringColumn
import org.incava.mesa.Table
import org.incava.mmonkeys.corpus.Corpus
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.time.WindowedClock
import java.io.PrintStream
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class SimMatchView(val corpus: Corpus, private val outputInterval: Int, private val out: PrintStream = System.out) {
    private val startTime: ZonedDateTime = ZonedDateTime.of(0, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC"))
    private val pattern: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
    private val numWords = corpus.numWords()
    private val table: Table
    private var linesUntilHeader: Int = 0
    private val clock = WindowedClock(1000)
    private val updates = mutableMapOf<ZonedDateTime, MutableList<SimUpdate>>()

    init {
        table = Table(
            out = out,
            columns = listOf(
                StringColumn("real time", 20, leftJustified = false),
                // we'll get a + when the year is over something? 10,000?
                StringColumn("sim time", 24, leftJustified = false),
                LongColumn("monkey", 8),
                LongColumn("#total keys", 12),
                StringColumn("word", 16, leftJustified = true),
                IntColumn("#keys", 5),
                LongColumn("index", 8),
                LongColumn("word ~", 8),
                LongColumn("word #", 8),
                LongColumn("total ~", 8),
                LongColumn("total #", 8),
            )
        )
    }

    private fun formatTime(dateTime: ZonedDateTime): String = dateTime.format(pattern)

    fun update(monkey: Monkey, matchingIndices: List<Int>, matchCount: Int, totalKeystrokes: Long) {
        matchingIndices.forEach { update(monkey, it, matchCount, totalKeystrokes, totalKeystrokes + corpus.lengthAtIndex(it)) }
    }

    fun update(monkey: Monkey, matchingIndex: Int, matchCount: Int, startAtKeystroke: Long, doneAtKeystroke: Long) {
        // @todo - use start time instead of done time?
        val doneTime = startTime.plusSeconds(doneAtKeystroke)
        updates.computeIfAbsent(doneTime) { mutableListOf() }
            .also { it += SimUpdate(monkey.id, startAtKeystroke, matchingIndex, matchCount) }
        val times = clock.pushTime(doneTime)
        times.forEach { time ->
            val updates = updates.remove(time)
            updates?.forEach { update -> update(update, time) }
        }
    }

    private fun update(update: SimUpdate, simTime: ZonedDateTime) {
        val word = corpus.wordAtIndex(update.index)
        val wordCount = (0 until numWords).filter {
            corpus.wordAtIndex(it) == word
        }
        val numWordMatches = wordCount.filter {
            corpus.matches.isMatched(it)
        }.size
        if (linesUntilHeader == 0) {
            out.println()
            table.writeHeader()
            linesUntilHeader = 9
        } else {
            --linesUntilHeader
        }
        val values = listOf(
            formatTime(ZonedDateTime.now()),
            formatTime(simTime),
            update.id,
            update.totalKeystrokes,
            word,
            word.length,
            update.index,
            numWordMatches,
            wordCount.size,
            update.matchCount,
            numWords
        )
        table.writeRow(values)
    }
}
