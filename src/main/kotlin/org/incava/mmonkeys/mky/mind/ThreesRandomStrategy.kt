package org.incava.mmonkeys.mky.mind

import org.incava.mmonkeys.rand.Sequences

class ThreesRandomStrategy(sequences: Sequences) : ThreesStrategy(sequences) {
    constructor(words: List<String>) : this(Sequences(words))

    private val threes = sequences.threes

    override fun getChar(firstChar: Char, secondChar: Char): Char {
        return SequencesStrategy.getRandomChar(threes, firstChar, secondChar)
    }

    override fun getChar(firstChar: Char): Char {
        return SequencesStrategy.getRandomChar(threes, firstChar)
    }

    override fun getChar(): Char {
        return SequencesStrategy.getRandomChar(threes)
    }
}