package org.incava.mmonkeys.util

import org.incava.mesa.IntColumn
import org.incava.mesa.LongColumn
import org.incava.mesa.StringColumn
import org.incava.mesa.Table
import org.incava.mmonkeys.mky.MatchData
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.corpus.Corpus
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

open class SimClockBase {
    val startTime: ZonedDateTime = ZonedDateTime.of(0, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC"))
    val pattern = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")

    fun formatTime(dateTime: ZonedDateTime) = dateTime.format(pattern)
}

class SimClock4(val corpus: Corpus) : SimClockBase() {
    private val numWords = corpus.words.size
    private val table: Table

    init {
        table = Table(
            listOf(
                StringColumn("real time", 24, leftJustified = true),
                StringColumn("sim time", 24, leftJustified = true),
                LongColumn("monkey", 8),
                StringColumn("word", 8, leftJustified = true),
                IntColumn("keystrokes", 10),
                LongColumn("word index", 10),
                LongColumn("#match word", 12),
                LongColumn("word count", 10),
                LongColumn("match total", 12),
                LongColumn("total count", 12),
            )
        )
    }

    fun writeMatch(monkey: Monkey, matchData: MatchData, secondsFromStart: Long, matchCount: Int) {
        val word = corpus.words[matchData.index]
        val wordCount = (0 until numWords).filter {
            corpus.words[it] == word
        }
        val numWordMatches = wordCount.filter {
            corpus.matched.contains(it)
        }.size
        if (matchCount % 10 == 0) {
            println()
            table.writeHeader()
            table.writeBreak('-')
        }

        table.writeRow(
            listOf(
                formatTime(ZonedDateTime.now()),
                formatTime(startTime.plusSeconds(secondsFromStart)),
                monkey.id,
                word,
                matchData.keystrokes,
                matchData.index,
                numWordMatches,
                wordCount.size,
                matchCount,
                numWords
            )
        )
    }
}
