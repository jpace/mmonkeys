package org.incava.mesa

import java.io.PrintStream

class TableY(out: PrintStream) : Table(out) {
    override fun columns(): List<Column> {
        return listOf(
            StringColumn("elapsed", 7),
            LongColumn("number", 14),
            LongColumn("free", 6),
            LongColumn("used", 6),
            LongColumn("total", 6),
        )
    }
}

class TableZ(out: PrintStream) : Table(out) {
    override fun columns(): List<Column> {
        return listOf(
            StringColumn("h1", 5),
            LongColumn("h2", 4),
            IntColumn("h3", 3),
        )
    }
}
