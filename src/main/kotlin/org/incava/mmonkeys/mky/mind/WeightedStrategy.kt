package org.incava.mmonkeys.mky.mind

import org.incava.mmonkeys.corpus.CorpusTraits
import org.incava.mmonkeys.rand.CharsRandom

class WeightedStrategy(words: List<String>) : TypeStrategy() {
    val chars: CharsRandom

    init {
        val counts = CorpusTraits(words).characterCounts()
        chars = CharsRandom(counts)
    }

    override fun getNextChar(): Char {
        return chars.nextDistributedRandom()
    }
}