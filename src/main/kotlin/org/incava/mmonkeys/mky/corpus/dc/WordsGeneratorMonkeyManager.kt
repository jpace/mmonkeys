package org.incava.mmonkeys.mky.corpus.dc

import org.incava.mmonkeys.mky.MonkeyMonitor

class WordsGeneratorMonkeyManager(val manager: MonkeyMonitor, val corpus: DualCorpus) {
    var id = 1

    fun createMonkey(): WordsGeneratorMonkey {
        val generator = WordsGeneratorFactory.createWithDefaults(corpus)
        val typewriter = AttemptedTypewriter(manager)
        return WordsGeneratorMonkey(id++, generator, typewriter)
    }
}