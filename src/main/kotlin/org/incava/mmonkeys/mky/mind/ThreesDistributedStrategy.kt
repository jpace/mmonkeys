package org.incava.mmonkeys.mky.mind

import org.incava.mmonkeys.rand.Sequences
import org.incava.mmonkeys.rand.CharDistRandom
import org.incava.mmonkeys.rand.CharRandom

class ThreesDistributedStrategy(sequences: Sequences) : ThreesStrategy(sequences) {
    constructor(words: List<String>) : this(Sequences(words))

    private val firsts: CharDistRandom
    private val seconds: Map<Char, CharDistRandom>
    private val thirds: Map<Char, Map<Char, CharDistRandom>>

    init {
        firsts = CharRandom.createFirsts3(sequences.threes)
        seconds = CharRandom.createSeconds3(sequences.threes)
        thirds = CharRandom.createThirds3(sequences.threes)
    }

    override fun getChar(firstChar: Char, secondChar: Char): Char {
        return CharRandom.getChar(thirds, firstChar, secondChar)
    }

    override fun getChar(firstChar: Char): Char = CharRandom.getChar(seconds, firstChar)
    override fun getChar(): Char = CharRandom.getChar(firsts)
}