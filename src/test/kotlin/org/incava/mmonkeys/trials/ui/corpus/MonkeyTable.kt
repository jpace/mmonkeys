package org.incava.mmonkeys.trials.ui.corpus

import org.incava.mesa.IntColumn
import org.incava.mesa.LongColumn
import org.incava.mesa.StringColumn
import org.incava.mesa.Table
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.mgr.Manager
import org.incava.mmonkeys.testutil.StringUtil

class MonkeyTable(private val maxWordLength: Int) : Table(
    listOf(
        IntColumn("id", 4),
        StringColumn("type", 16, leftJustified = true),
        LongColumn("attempts", 16),
        LongColumn("keystrokes", 14),
        LongColumn("matches#", 8),
    ) + (1..maxWordLength).map { LongColumn("match $it", 8) }
) {
    fun write(monkey: Monkey, manager: Manager) {
        writeHeader()
        val numMatches = manager.matchCount()
        val matchesByLength = manager.matchesByLength()
        val values = listOf(
            monkey.id,
            StringUtil.camelCaseToWords(monkey.javaClass.simpleName),
            manager.attemptCount(),
            manager.keystrokesCount(),
            numMatches
        ) + (1..maxWordLength).map { matchesByLength[it] ?: 0 }
        writeRow(values)
        writeBreak('-')
        println()
    }
}