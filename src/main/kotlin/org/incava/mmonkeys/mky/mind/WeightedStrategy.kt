package org.incava.mmonkeys.mky.mind

import org.incava.mmonkeys.corpus.CorpusTraits
import org.incava.mmonkeys.rand.DistributedRandom

class WeightedStrategy(words: List<String>) : TypeStrategy() {
    val chars: DistributedRandom<Char, Int>

    init {
        val byChar = CorpusTraits(words).characterCounts()
        chars = DistributedRandom(byChar)
    }

    override fun getNextChar(): Char {
        return chars.nextRandom()
    }
}