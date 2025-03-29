package org.incava.mmonkeys.mky.mind

import org.incava.mmonkeys.mky.corpus.sc.Sequences

class SequencesTwo(sequences: Sequences) {
    private val twos = sequences.twos

    fun getRandomChar(firstChar: Char): Char {
        return SequencesStrategy.getRandomChar(twos, firstChar)
    }

    fun getRandomChar(): Char {
        return SequencesStrategy.getRandomChar(twos)
    }
}