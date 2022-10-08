package org.incava.mesa

import java.io.PrintStream

abstract class Table(private val out: PrintStream = System.out, private val writer: TableWriter = TableWriter(out)) {
    abstract fun cells(): List<Triple<String, Class<out Any>, Int>>

    fun columns(): Array<Column> {
        return cells().map { Column(it.first, it.second, it.third) }.toTypedArray()
    }

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