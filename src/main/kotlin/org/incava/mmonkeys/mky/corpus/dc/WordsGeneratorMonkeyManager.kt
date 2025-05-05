package org.incava.mmonkeys.mky.corpus.dc

class WordsGeneratorMonkeyManager(val corpus: DualCorpus) {
    var id = 1

    fun createMonkey(): WordsGeneratorMonkey {
        val generator = WordsGeneratorFactory.createWithDefaults(corpus)
        val typewriter = AttemptedTypewriter()
        return WordsGeneratorMonkey(id++, generator, typewriter)
    }
}