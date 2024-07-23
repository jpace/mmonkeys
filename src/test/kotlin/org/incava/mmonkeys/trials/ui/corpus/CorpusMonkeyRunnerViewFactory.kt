package org.incava.mmonkeys.trials.ui.corpus

import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.trials.ui.ViewType

class CorpusMonkeyRunnerViewFactory<T : Corpus> {
    fun create(type: ViewType, corpus: T, verbose: Boolean): CorpusMonkeyRunnerView<T> {
        return when (type) {
            ViewType.CONSOLE -> CorpusMonkeyRunnerConsoleView(corpus, verbose)
            ViewType.TABLE -> CorpusMonkeyRunnerTableView(corpus, verbose)
        }
    }
}
