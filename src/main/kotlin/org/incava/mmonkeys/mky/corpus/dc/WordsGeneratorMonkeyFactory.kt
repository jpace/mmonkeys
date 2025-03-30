package org.incava.mmonkeys.mky.corpus.dc

object WordsGeneratorMonkeyFactory {
    fun createMonkey(id: Int, corpus: DualCorpus) : WordsGeneratorMonkey {
        val generator = WordsGeneratorFactory.createWithDefaults(corpus)
        return WordsGeneratorMonkey(id, generator)
    }
}