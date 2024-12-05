package org.incava.mmonkeys.mky.corpus

import org.incava.mesa.Column
import org.incava.mesa.DoubleColumn
import org.incava.mesa.IntColumn
import org.incava.mesa.LongColumn
import org.incava.mesa.Table
import java.io.PrintStream

class CorpusStatsView(val corpus: Corpus, private val outputInterval: Int, val out: PrintStream = System.out) {
    private val columns: List<Column> = listOf(
        IntColumn("length", 12),
        LongColumn("matched", 12),
        LongColumn("total", 12),
        IntColumn("matched %", 12),
    )
    private val table: Table = Table(columns, out = out)

    init {
        show()
    }

    fun update(matchCount: Int, totalKeystrokes: Long) {
        if (matchCount % outputInterval == 0) {
            show()
        }
    }

    fun show() {
        out.print("\u001b[H\u001b[2J")
        table.writeHeader('=')
        val countByLength = corpus.words.groupingBy { it.length }.eachCount()
        val matchedByLength = mutableMapOf<Int, Int>()
        corpus.words.withIndex().forEach { (index, word) ->
            if (corpus.matched.contains(index)) {
                val len = word.length
                matchedByLength[len] = (matchedByLength[len] ?: 0) + 1
            }
        }
        countByLength.toSortedMap().forEach { (length, count) ->
            val numMatches = matchedByLength.getOrDefault(length, 0)
            val pct = (100 * numMatches.toDouble() / count).toInt()
            table.writeRow(listOf(length, count, numMatches, pct))
        }
    }
}