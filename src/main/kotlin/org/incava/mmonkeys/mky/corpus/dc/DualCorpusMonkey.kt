package org.incava.mmonkeys.mky.corpus.dc

import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.words.Words

class DualCorpusMonkey(id: Int, corpus: DualCorpus) : Monkey(id, corpus) {
    private val generator = WordsGeneratorFactory.createWithDefaults(corpus)

    override fun findMatches(): Words {
        return generator.attemptMatch().asWords().also { recordWords(it) }
    }
}