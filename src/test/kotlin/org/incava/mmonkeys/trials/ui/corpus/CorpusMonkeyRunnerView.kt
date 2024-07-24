package org.incava.mmonkeys.trials.ui.corpus

import org.incava.mmonkeys.mky.MatchData
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.corpus.Corpus

abstract class CorpusMonkeyRunnerView<T : Corpus>(val corpus: T, private val verbose: Boolean) {
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
