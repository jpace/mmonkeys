package org.incava.mmonkeys.mky.mind

import org.incava.mmonkeys.mky.corpus.sc.Sequences

class ThreesRandomStrategy(sequences: Sequences) : ThreesStrategy(sequences) {
    constructor(words: List<String>) : this(Sequences(words))

    private val threes = sequences.threes

    override fun getChar(firstChar: Char, secondChar: Char): Char {
        return threes.getValue(firstChar).getValue(secondChar).keys.random()
    }

    override fun getChar(firstChar: Char): Char {
        return threes.getValue(firstChar).keys.random()
    }

    override fun getFirstChar(): Char {
        return threes.keys.random()
    }
}