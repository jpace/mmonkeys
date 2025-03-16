package org.incava.mmonkeys.mky.corpus.sc

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.mky.corpus.type.TypeStrategy
import org.incava.mmonkeys.rand.DistributedRandom

class WeightedStrategy(val words: List<String>) : TypeStrategy() {
    private val random: DistributedRandom<Char, Int>

    init {
        val byChar = CorpusTraits(words).characterCounts()
        Qlog.info("byChar", byChar.toSortedMap())
        random = DistributedRandom(byChar)
    }

    override fun typeCharacter(): Char {
        return random.nextRandom()
    }
}