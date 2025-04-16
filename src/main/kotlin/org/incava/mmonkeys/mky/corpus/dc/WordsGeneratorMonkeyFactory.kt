package org.incava.mmonkeys.mky.corpus.dc

import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter

object WordsGeneratorMonkeyFactory {
    fun createMonkey(id: Int, corpus: DualCorpus) : WordsGeneratorMonkey {
        val generator = WordsGeneratorFactory.createWithDefaults(corpus)
        val typewriter = Typewriter(Keys.fullList())
        return WordsGeneratorMonkey(id, generator, typewriter)
    }
}