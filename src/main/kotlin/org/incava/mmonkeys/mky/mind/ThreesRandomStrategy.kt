package org.incava.mmonkeys.mky.mind

import org.incava.mmonkeys.rand.Sequences
import org.incava.mmonkeys.rand.SequencesFactory

class ThreesRandomStrategy(sequences: Sequences) : ThreesStrategy(sequences) {
    constructor(words: List<String>) : this(SequencesFactory.createFromWords(words))

    private val threes = sequences.threes

    override fun getChar(firstChar: Char, secondChar: Char): Char {
        return threes.random().getChar(firstChar, secondChar)
    }

    override fun getChar(firstChar: Char): Char {
        return threes.random().getChar(firstChar)
    }

    override fun getChar(): Char {
        return threes.random().getChar()
    }
}