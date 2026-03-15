package org.incava.mmonkeys.mky.mind

import org.incava.mmonkeys.rand.Sequences
import org.incava.mmonkeys.rand.CharRandom
import org.incava.mmonkeys.rand.CharsSlots
import org.incava.mmonkeys.rand.SequencesFactory

class TwosDistributedStrategy(sequences: Sequences) : TwosStrategy(sequences) {
    constructor(words: List<String>) : this(SequencesFactory.createFromWords(words))

    private val firsts: CharsSlots
    private val seconds: Map<Char, CharsSlots>

    init {
        firsts = CharRandom.createFirsts(sequences.twos)
        seconds = CharRandom.createSeconds(sequences.twos)
    }

    override fun getChar(firstChar: Char): Char = CharRandom.getChar(seconds, firstChar)
    override fun getChar(): Char = firsts.distributed.getChar()
}