package org.incava.mmonkeys.mky.corpus.sc

import org.incava.mmonkeys.rand.DistributedRandom

class WeightFilter(val words: List<String>) {
    private val random: DistributedRandom<Char, Int>

    init {
        val byChar = CorpusTraits().characterCounts(words)
        random = DistributedRandom(byChar)
    }

    fun nextCharacter(): Char {
        return random.nextRandom()
    }
}