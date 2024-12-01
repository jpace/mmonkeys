package org.incava.mmonkeys.mky

import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.words.Words

class DualCorpusMonkey(id: Int, override val corpus: Corpus) : Monkey(id, corpus) {
    override fun findMatches(): Words {
        TODO("Not yet implemented")
    }
}