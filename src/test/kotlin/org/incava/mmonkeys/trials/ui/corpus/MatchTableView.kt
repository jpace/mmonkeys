package org.incava.mmonkeys.trials.ui.corpus

import org.incava.mesa.Column
import org.incava.mesa.IntColumn
import org.incava.mesa.LongColumn
import org.incava.mesa.StringColumn
import org.incava.mesa.Table
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.MonkeyMonitor
import org.incava.mmonkeys.corpus.Corpus
import org.incava.mmonkeys.mky.mgr.Manager

class MatchTableView<T : Corpus>(corpus: T, verbose: Boolean) : MatchView<T>(corpus, verbose) {
    private val columns: List<Column> = listOf(
        StringColumn("match?", 8),
        IntColumn("sought.matched.#", 16),
        IntColumn("sought.words.#", 16),
        StringColumn("sought.empty?", 16, leftJustified = true),
        LongColumn("invocations", 12),
        StringColumn("monkey.class", 16),
        LongColumn("result.#keys", 16),
        IntColumn("result.index", 16),
        StringColumn("word", 16)
    )
    val table: Table = Table(columns)

    override fun showStart() {
        table.writeHeader()
    }

    override fun showResult(monkey: Monkey, manager: Manager, result: Int?) {
        val values = listOf(
            result != null,
            corpus.matches.count(),
            corpus.numWords(),
            !manager.hasUnmatched(),
            manager.attemptCount()
        ) + if (result == null) {
            listOf("n/a", -1, -1, "")
        } else {
            val word = corpus.wordAtIndex(result)
            val length = word.length
            listOf(monkey.javaClass, length, result, word)
        }
        table.writeRow(values)
    }
}
