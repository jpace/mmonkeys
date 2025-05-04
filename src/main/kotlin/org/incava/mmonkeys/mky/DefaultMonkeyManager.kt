package org.incava.mmonkeys.mky

import org.incava.mmonkeys.corpus.Corpus
import org.incava.mmonkeys.mky.mind.RandomStrategy
import org.incava.mmonkeys.mky.mind.TypeStrategy
import org.incava.mmonkeys.type.DefaultTypewriter
import org.incava.mmonkeys.type.Keys

class DefaultMonkeyManager(val corpus: Corpus) {
    var id = 1
    val checker = WordChecker(corpus)

    fun createMonkeyRandom(): DefaultMonkey {
        val strategy = RandomStrategy(Keys.fullList())
        val typewriter = DefaultTypewriter(checker)
        return DefaultMonkey(id++, strategy, typewriter)
    }

    fun createMonkey(strategy: TypeStrategy): DefaultMonkey {
        val typewriter = DefaultTypewriter(checker)
        return DefaultMonkey(id++, strategy, typewriter)
    }
}