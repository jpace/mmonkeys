package org.incava.mmonkeys.trials.ui.corpus

import org.incava.mesa.IntColumn
import org.incava.mesa.LongColumn
import org.incava.mesa.Table
import org.incava.mmonkeys.mky.Monkey

class CorpusMonkeyTable : Table(
    listOf(
        IntColumn("id", 8),
        LongColumn("attempts", 16),
        LongColumn("keystrokes", 16),
        LongColumn("keys/attempt", 12),
    )
) {
    fun write(monkey: Monkey) {
        writeHeader()
        writeBreak('=')
        writeRow(monkey.id, monkey.numAttempts, monkey.totalKeystrokes, monkey.totalKeystrokes / monkey.numAttempts)
        writeBreak('-')
    }
}