package org.incava.mmonkeys.mky.corpus

import org.incava.mesa.Column
import org.incava.mesa.IntColumn
import org.incava.mesa.LongColumn
import org.incava.mesa.Table

class CorpusView(val corpus: Corpus) {
    fun show() {
        val words = corpus.words
        val byLength = words.fold(mutableMapOf<Int, Int>()) { acc, word ->
            acc.merge(word.length, 1) { prev, _ -> prev + 1 }
            acc
        }

        val columns = mutableListOf<Column>()
        val values = mutableListOf<Any>()
        columns += LongColumn("words.#", 12)
        values += words.size
        columns += LongColumn("uniq.#", 12)
        values += words.toSet().size
        byLength.toSortedMap().forEach { (length, count) ->
            columns += IntColumn("$length", 8)
            values += count
        }

        val table = Table(columns)
        table.writeHeader()
        table.writeBreak('=')
        table.writeRow(values)
        println()
    }
}