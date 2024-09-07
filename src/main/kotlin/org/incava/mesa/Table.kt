package org.incava.mesa

import java.io.PrintStream

open class Table(
    private val columns: List<Column>,
    private val breakInterval: Int = -1,
    private val breakChar: Char = '-',
    private val out: PrintStream = System.out,
) {
    private var rowsWritten = 0

    fun writeHeader() {
        val names = columns.map(Column::formatHeader)
        writeStrings(names)
        writeBreak(breakChar)
    }

    fun writeBreak(char: Char) {
        val breaks = columns.map { it.formatBreak(char) }
        rowsWritten = 0
        writeStrings(breaks)
    }

    fun writeRow(values: List<Any>) {
        if (breakInterval > 0 && rowsWritten == breakInterval) {
            writeBreak(breakChar)
        }
        ++rowsWritten
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