package org.incava.mmonkeys.mky

import org.incava.mmonkeys.corpus.Corpus
import org.incava.mmonkeys.mky.mgr.Manager
import org.incava.mmonkeys.mky.mind.RandomStrategy
import org.incava.mmonkeys.mky.mind.TypeStrategy
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.TypewriterFactory

object DefaultMonkeyFactory {
    var id = 1

    fun createMonkeyRandom(id: Int, corpus: Corpus): DefaultMonkey {
        return createMonkey(id, corpus, RandomStrategy(Keys.fullList()))
    }

    fun createMonkey(id: Int, corpus: Corpus, strategy: TypeStrategy): DefaultMonkey {
        val checker = WordChecker(corpus)
        return createMonkey(id, checker, strategy)
    }

    fun createMonkey(id: Int, checker: WordChecker, strategy: TypeStrategy): DefaultMonkey {
        val typewriter = TypewriterFactory.create()
        return DefaultMonkey(id, checker, strategy, typewriter)
    }
}