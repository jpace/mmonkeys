package org.incava.mmonkeys.mky.corpus.sc.map

import org.incava.mmonkeys.mky.corpus.sc.CorpusMonkey
import org.incava.mmonkeys.mky.mind.RandomStrategy
import org.incava.mmonkeys.type.Keys

object MapMonkeyFactory {
    fun create(id: Int, corpus: MapCorpus): CorpusMonkey {
        val strategy = RandomStrategy(Keys.fullList())
        return CorpusMonkey(id, MapWordChecker(corpus), strategy)
    }
}