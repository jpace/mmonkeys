package org.incava.mmonkeys.trials.ui.corpus

import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.MonkeyMonitor
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.trials.ui.ViewType

abstract class MatchView<T : Corpus>(val corpus: T, private val verbose: Boolean) {
    companion object {
        fun <U : Corpus> createView(type: ViewType, corpus: U, verbose: Boolean): MatchView<U> {
            return when (type) {
                ViewType.CONSOLE -> MatchConsoleView(corpus, verbose)
                ViewType.TABLE -> MatchTableView(corpus, verbose)
            }
        }
    }

    open fun showStart() {}

    abstract fun showResult(monkey: Monkey, manager: MonkeyMonitor, result: Int?)

    fun init() {
        if (verbose) {
            showStart()
        }
    }

    fun show(monkey: Monkey, manager: MonkeyMonitor, result: Int?) {
        if (verbose) {
            showResult(monkey, manager, result)
        }
    }
}
