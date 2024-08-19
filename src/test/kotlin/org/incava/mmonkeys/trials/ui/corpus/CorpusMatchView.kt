package org.incava.mmonkeys.trials.ui.corpus

import org.incava.mesa.LongColumn
import org.incava.mesa.StringColumn
import org.incava.mesa.Table
import org.incava.mmonkeys.trials.base.PerfResults

class CorpusMatchView(private val wordSizeLimit: Int, val results: Map<String, PerfResults>) {
    val table: Table

    init {
        val cells = listOf(
            StringColumn("type", 10, leftJustified = true)
        ) + results.keys.map { name -> LongColumn(name, 10) } +
                LongColumn("total", 10)
        table = Table(cells)
    }

    fun summarize() {
        table.writeHeader()
        table.writeBreak('=')
        (1..wordSizeLimit).forEach { length ->
            val matchCells = mutableListOf<Any>("match $length")
            val countCells = mutableListOf<Any>("count $length")
            results.forEach { (name, res) ->
                matchCells += matchesForLength(res, length)
            }
            // all corpuses have the same number of words
            matchCells += results.values.first().corpus.words.count { it.length == length }
            table.writeRow(matchCells)
        }
        table.writeBreak('-')
    }

    fun matchesForLength(results: PerfResults, length: Int): Int {
        return results.corpus.matched.filter { results.corpus.words[it].length == length }.size
    }
}