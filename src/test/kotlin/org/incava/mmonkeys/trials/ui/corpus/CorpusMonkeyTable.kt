package org.incava.mmonkeys.trials.ui.corpus

import org.incava.mesa.IntColumn
import org.incava.mesa.LongColumn
import org.incava.mesa.StringColumn
import org.incava.mesa.Table
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.testutil.StringUtil

class CorpusMonkeyTable(private val maxWordLength: Int) : Table(
    listOf(
        IntColumn("id", 4),
        StringColumn("type", 16, leftJustified = true),
        LongColumn("attempts", 14),
        LongColumn("keystrokes", 14),
        LongColumn("matches#", 8),
    ) + (1..maxWordLength).map { IntColumn("match $it", 8) }
) {
    fun write(monkey: Monkey) {
        // this requires that a monkey have a monitor
        writeHeader()
        writeBreak('=')
        val monitor = monkey.monitors.first()
        val numMatches = monitor.matchCount()
        val values = listOf(
            monkey.id,
            StringUtil.camelCaseToWords(monkey.javaClass.simpleName),
            monitor.attemptCount(),
            monitor.keystrokesCount(),
            numMatches
        ) + (1..maxWordLength).map { monkey.matchKeystrokes[it] ?: 0 }
        writeRow(values)
        writeBreak('-')
    }
}