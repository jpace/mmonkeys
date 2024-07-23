package org.incava.mmonkeys.mky.corpus

import org.incava.mesa.Column
import org.incava.mesa.IntColumn
import org.incava.mesa.LongColumn
import org.incava.mesa.Table

class CorpusView(val corpus: Corpus) {
    private val columns: List<Column>
    private val values: List<Any>

    init {
        val byLength = corpus.words.fold(mutableMapOf<Int, Int>()) { acc, word ->
            acc.merge(word.length, 1) { prev, _ -> prev + 1 }
            acc
        }
        values = listOf(corpus.words.size, corpus.words.toSet().size) + byLength.toSortedMap().values
        columns = listOf(LongColumn("words.#", 12), LongColumn("uniq.#", 12)) +
                byLength.toSortedMap().keys.map { length ->
                    IntColumn("$length", 8)
                }
    }

    fun show() {
        val table = Table(columns)
        table.writeHeader()
        table.writeBreak('=')
        table.writeRow(values)
        println()
    }
}