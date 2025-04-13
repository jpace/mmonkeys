package org.incava.mmonkeys.mky.corpus

import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.WordChecker
import org.incava.mmonkeys.mky.corpus.sc.DefaultMonkey
import org.incava.mmonkeys.mky.mind.RandomStrategy
import org.incava.mmonkeys.mky.mind.TypeStrategy
import org.incava.mmonkeys.type.Keys

object MonkeyFactory {
    fun createMonkeyRandom(id: Int, corpus: Corpus): Monkey {
        return createMonkey(id, corpus, RandomStrategy(Keys.fullList()))
    }

    fun createMonkey(id: Int, corpus: Corpus, strategy: TypeStrategy): Monkey {
        val checker = WordChecker(corpus)
        return createMonkey(id, checker, strategy)
    }

    fun createMonkey(id: Int, checker: WordChecker, strategy: TypeStrategy): Monkey {
        return DefaultMonkey(id, checker, strategy)
    }
}