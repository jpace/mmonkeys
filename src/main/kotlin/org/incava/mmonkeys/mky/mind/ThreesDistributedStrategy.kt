package org.incava.mmonkeys.mky.mind

import org.incava.mmonkeys.mky.corpus.sc.Sequences
import org.incava.mmonkeys.rand.DistributedRandom

class ThreesDistributedStrategy(sequences: Sequences) : ThreesStrategy(sequences) {
    constructor(words: List<String>) : this(Sequences(words))

    private val firsts: DistributedRandom<Char, Int>
    private val seconds: Map<Char, DistributedRandom<Char, Int>>
    private val thirds: Map<Char, Map<Char, DistributedRandom<Char, Int>>>

    init {
        firsts = DistributedStrategy.createFirsts3(sequences.threes)
        seconds = DistributedStrategy.createSeconds3(sequences.threes)
        thirds = DistributedStrategy.createThirds3(sequences.threes)
    }

    override fun getChar(firstChar: Char, secondChar: Char): Char {
        return DistributedStrategy.getChar(thirds, firstChar, secondChar)
    }

    override fun getChar(firstChar: Char): Char = DistributedStrategy.getChar(seconds, firstChar)
    override fun getChar(): Char = DistributedStrategy.getChar(firsts)
}