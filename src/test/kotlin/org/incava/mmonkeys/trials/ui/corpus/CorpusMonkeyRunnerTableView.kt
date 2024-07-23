package org.incava.mmonkeys.trials.ui.corpus

import org.incava.mesa.Column
import org.incava.mesa.IntColumn
import org.incava.mesa.LongColumn
import org.incava.mesa.StringColumn
import org.incava.mesa.Table
import org.incava.mmonkeys.mky.MatchData
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.corpus.Corpus

class CorpusMonkeyRunnerTableView<T : Corpus>(corpus: T, verbose: Boolean) :
    CorpusMonkeyRunnerView<T>(corpus, verbose) {
    private val columns: List<Column> = listOf(
        StringColumn("match?", 8),
        IntColumn("sought.matched.#", 16),
        IntColumn("sought.words.#", 16),
        StringColumn("sought.empty?", 16, leftJustified = true),
        LongColumn("invocations", 12),
        StringColumn("monkey.class", 16),
        LongColumn("result.keystrokes", 16),
        IntColumn("result.index", 16),
        StringColumn("word", 16)
    )
    val table = Table(columns)

    override fun showStart() {
        table.writeHeader()
        table.writeBreak('=')
    }

    override fun showResult(monkey: Monkey, result: MatchData) {
        val values = listOf(
            result.isMatch,
            corpus.matched.size,
            corpus.words.size,
            corpus.isEmpty(),
            monkey.attempts.invocations
        ) + if (result.isMatch) {
            listOf("n/a", -1, -1, "")
        } else {
            listOf(monkey.javaClass, result.keystrokes, result.index, corpus.words[result.index])
        }
        table.writeRow(values)
    }
}
