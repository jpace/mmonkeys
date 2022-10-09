package org.incava.mmonkeys.util

import org.incava.mesa.Column
import org.incava.mesa.LongColumn
import org.incava.mesa.StringColumn
import org.incava.mesa.Table

class MemoryTable : Table() {
    override fun columns(): List<Column> {
        return listOf(
            StringColumn("elapsed", 7),
            LongColumn("number", 14),
            LongColumn("free", 6),
            LongColumn("used", 6),
            StringColumn("total", 6),
        )
    }
}