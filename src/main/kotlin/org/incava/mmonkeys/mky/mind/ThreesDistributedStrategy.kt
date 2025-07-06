package org.incava.mmonkeys.mky.mind

import org.incava.mmonkeys.rand.Sequences
import org.incava.mmonkeys.rand.SequencesFactory

class ThreesDistributedStrategy(sequences: Sequences) : ThreesStrategy(sequences) {
    constructor(words: List<String>) : this(SequencesFactory.createFromWords(words))

    override fun getChar(firstChar: Char, secondChar: Char): Char {
        return sequences.threes.dists().getChar(firstChar, secondChar)
    }

    override fun getChar(firstChar: Char): Char = sequences.threes.dists().getChar(firstChar)

    override fun getChar(): Char = sequences.threes.dists().getChar()
}