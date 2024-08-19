package org.incava.mmonkeys.mky.corpus

import org.incava.mesa.Column
import org.incava.mesa.LongColumn
import org.incava.mesa.StringColumn
import org.incava.mesa.Table

class CorpusView(val corpus: Corpus) {
    private val columns: List<Column>

    init {
        columns = listOf(
            StringColumn("type", 12, leftJustified = true),
            LongColumn("count", 12)
        )
    }

    fun show() {
        val table = Table(columns)
        table.writeHeader()
        table.writeBreak('=')
        val byLength = corpus.words.groupingBy { it.length }.eachCount()
        byLength.toSortedMap().forEach { (length, count) ->
            table.writeRow(listOf("length $length", count))
        }
        table.writeRow(listOf("unique", corpus.words.toSet().size))
        table.writeRow(listOf("total", corpus.words.size))
        println()
    }
}