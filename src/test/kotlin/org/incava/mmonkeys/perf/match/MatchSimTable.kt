package org.incava.mmonkeys.perf.match

import org.incava.mesa.*

class MatchSimTable : Table() {
    override fun columns(): List<Column> {
        return listOf(
            IntStringColumn("trial", 5),
            StringColumn("type", 12, leftJustified = true),
            LongColumn("iterations", 15),
            LongColumn("duration", 15),
            LongColumn("iters/sec", 15),
        )
    }
}