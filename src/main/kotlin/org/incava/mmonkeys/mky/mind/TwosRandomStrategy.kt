package org.incava.mmonkeys.mky.mind

import org.incava.mmonkeys.mky.corpus.sc.Sequences

class TwosRandomStrategy(sequences: Sequences) : TwosStrategy(sequences) {
    constructor(words: List<String>) : this(Sequences(words))

    private val twos = sequences.twos

    override fun getChar(firstChar: Char): Char {
        return twos.getValue(firstChar).keys.random()
    }

    override fun getFirstChar(): Char {
        return twos.keys.random()
    }
}