package org.incava.mmonkeys.mky.mind

import org.incava.mmonkeys.rand.Sequences
import org.incava.mmonkeys.rand.SequencesFactory

class TwosRandomStrategy(sequences: Sequences) : TwosStrategy(sequences) {
    constructor(words: List<String>) : this(SequencesFactory.createFromWords(words))

    private val twos = SequencesTwo(sequences)

    override fun getChar(firstChar: Char): Char {
        return twos.getRandomChar(firstChar)
    }

    override fun getChar(): Char {
        return twos.getRandomChar()
    }
}