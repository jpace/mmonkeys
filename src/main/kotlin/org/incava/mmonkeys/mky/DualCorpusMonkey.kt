package org.incava.mmonkeys.mky

import org.incava.mmonkeys.mky.corpus.dc.DualCorpus
import org.incava.mmonkeys.mky.corpus.dc.WordsGeneratorFactory
import org.incava.mmonkeys.words.Words

class DualCorpusMonkey(id: Int, override val corpus: DualCorpus) : Monkey(id, corpus) {
    private val generator = WordsGeneratorFactory.createWithDefaults(corpus)

    override fun findMatches(): Words {
        return generator.findMatches().also { recordWords(it) }
    }
}