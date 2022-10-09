package org.incava.mesa

import java.io.PrintStream

abstract class Table(private val out: PrintStream = System.out, private val writer: TableWriter = TableWriter(out)) {
    abstract fun columns(): List<Column>

    fun printHeader() {
        writer.writeHeader(this)
    }

    fun printRow(values: List<Any>) {
        writer.writeRow(this, values)
    }

    fun printRow(vararg values: Any) {
        writer.writeRow(this, listOf(*values))
    }
}