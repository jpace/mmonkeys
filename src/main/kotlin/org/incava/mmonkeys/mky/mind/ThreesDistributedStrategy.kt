package org.incava.mmonkeys.mky.mind

import org.incava.mmonkeys.rand.Sequences
import org.incava.mmonkeys.rand.SequencesFactory

class ThreesDistributedStrategy(sequences: Sequences) : ThreesStrategy(sequences) {
    constructor(words: List<String>) : this(SequencesFactory.createFromWords(words))

    override fun getChar(firstChar: Char, secondChar: Char): Char {
        return threes.nextDistributedChar(firstChar, secondChar)
    }

    override fun getChar(firstChar: Char): Char = threes.nextDistributedChar(firstChar)

    override fun getChar(): Char = threes.nextDistributedChar()
}