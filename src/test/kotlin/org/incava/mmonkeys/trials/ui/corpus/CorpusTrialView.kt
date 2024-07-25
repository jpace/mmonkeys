package org.incava.mmonkeys.trials.ui.corpus

import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.trials.base.PerfResults

class CorpusTrialView(
    private val corpus: Corpus,
    private val wordSizeLimit: Int) {

    fun show(results: Map<String, PerfResults>) {
        val table = CorpusTrialTable(corpus.words.size, wordSizeLimit)
        table.summarize(results)
        println()
        val matchTable = CorpusMatchTable(wordSizeLimit, results)
        matchTable.summarize()
    }
}
