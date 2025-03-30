package org.incava.mmonkeys.mky.corpus.sc

import org.incava.mmonkeys.mky.WordChecker
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.mind.TypeStrategy

object CorpusMonkeyFactory {
    fun create(id: Int, corpus: Corpus, strategy: TypeStrategy): CorpusMonkey {
        val checker = WordChecker(corpus)
        return CorpusMonkey(id, checker, strategy)
    }
}