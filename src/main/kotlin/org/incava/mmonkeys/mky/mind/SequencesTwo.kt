package org.incava.mmonkeys.mky.mind

import org.incava.mmonkeys.rand.Sequences

class SequencesTwo(sequences: Sequences) {
    private val twos = sequences.twos

    fun getRandomChar(firstChar: Char): Char {
        return SequencesStrategy.getRandomChar(twos, firstChar)
    }

    fun getRandomChar(): Char {
        return SequencesStrategy.getRandomChar(twos)
    }
}