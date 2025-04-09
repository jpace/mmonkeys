package org.incava.mmonkeys.mky.mind

import org.incava.mmonkeys.rand.Sequences
import org.incava.mmonkeys.rand.CharDistRandom
import org.incava.mmonkeys.rand.CharRandom
import org.incava.mmonkeys.rand.SequencesFactory

class TwosDistributedStrategy(sequences: Sequences) : TwosStrategy(sequences) {
    constructor(words: List<String>) : this(SequencesFactory.createFromWords(words))

    private val firsts: CharDistRandom
    private val seconds: Map<Char, CharDistRandom>

    init {
        firsts = CharRandom.createFirsts2(sequences.twos)
        seconds = CharRandom.createSeconds2(sequences.twos)
    }

    override fun getChar(firstChar: Char): Char = CharRandom.getChar(seconds, firstChar)
    override fun getChar(): Char = CharRandom.getChar(firsts)
}