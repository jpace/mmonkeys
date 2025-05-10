package org.incava.mmonkeys.trials.ui.corpus

import org.incava.mesa.Column
import org.incava.mesa.IntColumn
import org.incava.mesa.StringColumn
import org.incava.mesa.Table
import org.incava.mmonkeys.trials.base.PerfResults

class CorpusMatchTable(private val wordSizeLimit: Int, val results: Map<String, PerfResults>) {
    val table: Table

    init {
        val cells = mutableListOf<Column>(
            StringColumn("type", 8, leftJustified = true),
        )
        (1 .. wordSizeLimit).map { length ->
            cells += IntColumn("match $length", 10)
            cells += IntColumn("count $length", 10)
        }
        table = Table(cells)
    }

    fun summarize() {
        table.writeHeader()
        table.writeBreak('=')
        results.forEach { (name, res) ->
            val matchesByLength = mutableMapOf<Int, Int>()
            val wordsByLength = mutableMapOf<Int, Int>()
            res.corpus.words().withIndex().forEach { (index, word) ->
                val wordLength = word.length
                if (res.corpus.isMatched(index)) {
                    matchesByLength[wordLength] = (matchesByLength[wordLength] ?: 0) + 1
                }
                wordsByLength[wordLength] = (wordsByLength[wordLength] ?: 0) + 1
            }
            val cells = mutableListOf<Any>(
                name,
            )
            (1..wordSizeLimit).forEach { length ->
                cells += matchesByLength.getOrDefault(length, 0)
                cells += wordsByLength.getOrDefault(length, 0)
            }
            table.writeRow(cells)
        }
        table.writeBreak('-')
    }
}