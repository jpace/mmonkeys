package org.incava.mesa

import java.io.PrintStream

class TableWriter(private val out: PrintStream = System.out) {
    fun writeLine(table: Table, fmt: (Column) -> String, args: Collection<Any>) {
        val formats = table.columns().map(fmt)
        val fmt = formats.joinToString(" | ")
        val ary = args.toTypedArray()
        val str = String.format(fmt, *ary)
        out.println(str)
    }

    fun writeHeader(table: Table) {
        val names = table.columns().map(Column::header)
        writeLine(table, Column::toHeaderFormat, names)
    }

    fun writeRow(table: Table, values: List<Any>) {
        writeLine(table, Column::toRowFormat, values)
    }
}