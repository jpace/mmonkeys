package org.incava.mmonkeys.perf.match

import org.incava.mesa.Column
import org.incava.mesa.IntStringColumn
import org.incava.mesa.LongColumn
import org.incava.mesa.StringColumn
import org.incava.mesa.Table

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