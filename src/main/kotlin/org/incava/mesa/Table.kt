package org.incava.mesa

import java.io.PrintStream

open class Table(private val columns: List<Column>, private val out: PrintStream = System.out) {
    fun writeHeader() {
        val names = columns.map(Column::formatHeader)
        writeStrings(names)
    }

    fun writeBreak(char: Char) {
        val breaks = columns.map { it.formatBreak(char) }
        writeStrings(breaks)
    }

    fun writeRow(values: List<Any>) {
        val line = columns.zip(values).joinToString(" | ") { (column, value) -> column.formatCell(value) }
        writeLine(line)
    }

    fun writeRow(vararg values: Any) {
        writeRow(values.toList())
    }

    private fun writeStrings(ary: List<String>) {
        val line = ary.joinToString(" | ")
        writeLine(line)
    }

    private fun writeLine(line: String) {
        out.println(line)
    }
}