package org.incava.mmonkeys.mky.mind

import org.incava.mmonkeys.rand.Char3Random
import org.incava.mmonkeys.rand.Sequences
import org.incava.mmonkeys.rand.CharDistRandom
import org.incava.mmonkeys.rand.CharRandom
import org.incava.mmonkeys.rand.SequencesFactory

class ThreesDistributedStrategy(sequences: Sequences) : ThreesStrategy(sequences) {
    constructor(words: List<String>) : this(SequencesFactory.createFromWords(words))

    private val firsts: CharDistRandom
    private val seconds: Map<Char, CharDistRandom>
    private val thirds: Map<Char, Map<Char, CharDistRandom>>

    init {
        firsts = Char3Random.createFirsts3(sequences.threes)
        seconds = Char3Random.createSeconds3(sequences.threes)
        thirds = Char3Random.createThirds3(sequences.threes)
    }

    override fun getChar(firstChar: Char, secondChar: Char): Char {
        return Char3Random.getChar(thirds, firstChar, secondChar)
    }

    override fun getChar(firstChar: Char): Char = CharRandom.getChar(seconds, firstChar)
    override fun getChar(): Char = CharRandom.getChar(firsts)
}