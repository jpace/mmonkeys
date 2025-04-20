package org.incava.mmonkeys.mky

import org.incava.mmonkeys.corpus.Corpus

open class CorpusUpdater(open val corpus: Corpus) {
    open fun indexMatched(index: Int) {
        corpus.setMatched(index)
    }
}
