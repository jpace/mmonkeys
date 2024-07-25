package org.incava.mmonkeys.trials.ui.corpus

import org.incava.mmonkeys.mky.MatchData
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.trials.ui.ViewType

abstract class CorpusMatchDataView<T : Corpus>(val corpus: T, private val verbose: Boolean) {
    companion object {
        fun <U : Corpus> createView(type: ViewType, corpus: U, verbose: Boolean): CorpusMatchDataView<U> {
            return when (type) {
                ViewType.CONSOLE -> CorpusMatchDataConsoleView(corpus, verbose)
                ViewType.TABLE -> CorpusMatchDataTableView(corpus, verbose)
            }
        }
    }

    open fun showStart() {}

    abstract fun showResult(monkey: Monkey, result: MatchData)

    fun init() {
        if (verbose) {
            showStart()
        }
    }

    fun show(monkey: Monkey, result: MatchData) {
        if (verbose) {
            showResult(monkey, result)
        }
    }
}
