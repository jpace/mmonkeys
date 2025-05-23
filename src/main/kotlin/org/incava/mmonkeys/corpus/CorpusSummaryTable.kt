package org.incava.mmonkeys.corpus

import org.incava.mesa.Column
import org.incava.mesa.LongColumn
import org.incava.mesa.StringColumn
import org.incava.mesa.Table

class CorpusSummaryTable(val corpus: Corpus) {
    private val columns: List<Column>

    init {
        columns = listOf(
            StringColumn("type", 12, leftJustified = true),
            LongColumn("count", 12)
        )
    }

    fun show() {
        val table = Table(columns)
        table.writeHeader('=')
        val words = corpus.words()
        val byLength = words.groupingBy { it.length }.eachCount()
        byLength.toSortedMap().forEach { (length, count) ->
            table.writeRow(listOf("length $length", count))
        }
        table.writeRow(listOf("unique", words.toSet().size))
        table.writeRow(listOf("total", corpus.numWords()))
        table.writeBreak('=')
        println()
    }
}