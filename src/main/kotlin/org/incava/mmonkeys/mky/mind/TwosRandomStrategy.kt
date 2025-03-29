package org.incava.mmonkeys.mky.mind

import org.incava.mmonkeys.mky.corpus.sc.Sequences

class TwosRandomStrategy(sequences: Sequences) : TwosStrategy(sequences) {
    constructor(words: List<String>) : this(Sequences(words))

    private val twos = SequencesTwo(sequences)

    override fun getChar(firstChar: Char): Char {
        return twos.getRandomChar(firstChar)
    }

    override fun getChar(): Char {
        return twos.getRandomChar()
    }
}