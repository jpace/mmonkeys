package org.incava.mmonkeys.mky.mind

import org.incava.mmonkeys.corpus.CorpusTraits
import org.incava.mmonkeys.rand.CharsSlots

class WeightedStrategy(words: List<String>) : TypeStrategy() {
    val chars: CharsSlots

    init {
        val counts = CorpusTraits(words).characterCounts()
        chars = CharsSlots(counts)
    }

    override fun getNextChar(): Char {
        return chars.distributed.getChar()
    }
}