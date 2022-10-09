package org.incava.mesa

import java.io.PrintStream

class TableX(out: PrintStream = System.out) : Table(out) {
    override fun columns(): List<Column> {
        return listOf(StringColumn("h1", 4))
    }
}

class TableY : Table() {
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
