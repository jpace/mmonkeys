package org.incava.mmonkeys.mky.mind

import org.incava.mmonkeys.rand.Sequences
import org.incava.mmonkeys.rand.CharRandom
import org.incava.mmonkeys.rand.CharsRandom
import org.incava.mmonkeys.rand.SequencesFactory

class TwosDistributedStrategy(sequences: Sequences) : TwosStrategy(sequences) {
    constructor(words: List<String>) : this(SequencesFactory.createFromWords(words))

    private val firsts: CharsRandom
    private val seconds: Map<Char, CharsRandom>

    init {
        firsts = CharRandom.createFirsts2(sequences.twos)
        seconds = CharRandom.createSeconds2(sequences.twos)
    }

    override fun getChar(firstChar: Char): Char = CharRandom.getChar(seconds, firstChar)
    override fun getChar(): Char = firsts.nextDistributedRandom()
}