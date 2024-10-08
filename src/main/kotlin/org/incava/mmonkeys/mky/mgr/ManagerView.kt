package org.incava.mmonkeys.mky.mgr

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

class ManagerView(
    val corpus: Corpus,
    private val manager: Manager,
    private val outputInterval: Int,
) {
    private val startTime: ZonedDateTime = ZonedDateTime.of(0, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC"))
    private val pattern: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
    private val numWords = corpus.words.size
    private val table: Table
    private var linesUntilHeader: Int = 0

    init {
        table = Table(
            listOf(
                StringColumn("real time", 20, leftJustified = false),
                // we'll get a + when the year is over something? 10,000?
                StringColumn("sim time", 24, leftJustified = false),
                LongColumn("monkey", 8),
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

    fun addMatch(monkey: Monkey, matchData: MatchData) {
        if (manager.matchCount() % outputInterval == 0) {
            writeMatch(monkey, matchData)
        }
    }

    fun writeMatch(monkey: Monkey, matchData: MatchData) {
        val word = corpus.words[matchData.index]
        val wordCount = (0 until numWords).filter {
            corpus.words[it] == word
        }
        val numWordMatches = wordCount.filter {
            corpus.matched.contains(it)
        }.size
        if (linesUntilHeader == 0) {
            println()
            table.writeHeader()
            table.writeBreak('-')
            linesUntilHeader = 9
        } else {
            --linesUntilHeader
        }
        // totalKeystrokes == virtual seconds:
        val values = listOf(
            formatTime(ZonedDateTime.now()),
            formatTime(startTime.plusSeconds(manager.totalKeystrokes)),
            monkey.id,
            word,
            matchData.keystrokes,
            matchData.index,
            numWordMatches,
            wordCount.size,
            manager.matchCount(),
            numWords
        )
        table.writeRow(values)
    }
}
