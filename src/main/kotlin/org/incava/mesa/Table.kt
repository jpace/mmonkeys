package org.incava.mesa

import java.io.PrintStream

abstract class Table(private val out: PrintStream = System.out) {
    abstract fun columns(): List<Column>

    fun writeHeader() {
        val names = columns().map(Column::formatHeader)
        writeStrings(names)
    }

    fun writeBreak(char: Char) {
        val sb = StringBuilder(char.toString())
        val chars = columns().map { it.headerFormat().format(sb.repeat(it.width)) }
        writeStrings(chars)
    }

    fun writeRow(values: List<Any>) {
        val cols = columns()
        val line = cols.indices.map { cols[it].formatCell(values[it]) }.joinToString(" | ")
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