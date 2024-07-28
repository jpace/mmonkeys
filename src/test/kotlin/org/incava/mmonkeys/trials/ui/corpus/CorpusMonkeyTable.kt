package org.incava.mmonkeys.trials.ui.corpus

import org.incava.mesa.IntColumn
import org.incava.mesa.LongColumn
import org.incava.mesa.StringColumn
import org.incava.mesa.Table
import org.incava.mmonkeys.mky.Monkey

class CorpusMonkeyTable(private val maxWordLength: Int) : Table(
    listOf(
        IntColumn("id", 8),
        StringColumn("type", 20, leftJustified = true),
        LongColumn("attempts", 16),
        LongColumn("keystrokes", 16),
        LongColumn("keys/attempt", 12),
    ) + (1 .. maxWordLength).map { IntColumn("match $it", 8) }
) {
    fun write(monkey: Monkey) {
        writeHeader()
        writeBreak('=')
        val values = listOf(
            monkey.id,
            monkey.javaClass.simpleName,
            monkey.attempts.count,
            monkey.attempts.totalKeystrokes,
            monkey.attempts.totalKeystrokes / monkey.attempts.count
        ) + (1 .. maxWordLength).map { monkey.matchKeystrokes[it] ?: 0 }
        writeRow(values)
        writeBreak('-')
    }
}