package org.incava.mmonkeys.mky.corpus.dc

import org.incava.mmonkeys.mky.mgr.Manager

class WordsGeneratorMonkeyManager(val manager: Manager, val corpus: DualCorpus) {
    var id = 1

    fun createMonkey(): WordsGeneratorMonkey {
        val generator = WordsGeneratorFactory.createWithDefaults(corpus)
        val typewriter = AttemptedTypewriter()
        return WordsGeneratorMonkey(id++, generator, typewriter, manager)
    }
}