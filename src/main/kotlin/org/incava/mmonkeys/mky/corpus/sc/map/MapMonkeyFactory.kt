package org.incava.mmonkeys.mky.corpus.sc.map

import org.incava.mmonkeys.mky.DefaultMonkey
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.DefaultMonkeyFactory
import org.incava.mmonkeys.mky.mind.RandomStrategy
import org.incava.mmonkeys.type.Keys

object MapMonkeyFactory {
    fun create(id: Int, corpus: MapCorpus): DefaultMonkey {
        val strategy = RandomStrategy(Keys.fullList())
        val checker = MapWordChecker(corpus)
        return DefaultMonkeyFactory.createMonkey(id, checker, strategy)
    }
}