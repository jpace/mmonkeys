package org.incava.mmonkeys.trials.ui.corpus

import org.incava.mmonkeys.trials.base.PerfResults

class CorpusTrialView(
    private val numWords: Int,
    private val wordSizeLimit: Int) {

    fun show(results: Map<String, PerfResults>) {
        val table = CorpusTrialTable(numWords, wordSizeLimit)
        table.summarize(results)
        println()
        val matchTable = CorpusMatchTable(wordSizeLimit, results)
        matchTable.summarize()
    }
}
