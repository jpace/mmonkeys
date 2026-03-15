package org.incava.mmonkeys.mky.mind

import org.incava.mmonkeys.rand.Sequences

class SequencesTwo(sequences: Sequences) {
    private val twos = sequences.twos

    fun getRandomChar(firstChar: Char): Char {
        val forChar = twos.getValue(firstChar)
        return forChar.keys.random()
    }

    fun getRandomChar(): Char {
        return twos.keys.random()
    }
}