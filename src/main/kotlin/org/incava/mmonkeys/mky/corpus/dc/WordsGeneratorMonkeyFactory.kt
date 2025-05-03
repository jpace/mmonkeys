package org.incava.mmonkeys.mky.corpus.dc

import org.incava.mmonkeys.type.TypewriterFactory

object WordsGeneratorMonkeyFactory {
    fun createMonkey(id: Int, corpus: DualCorpus): WordsGeneratorMonkey {
        val generator = WordsGeneratorFactory.createWithDefaults(corpus)
        val typewriter = TypewriterFactory.create()
        return WordsGeneratorMonkey(id, generator, typewriter)
    }
}