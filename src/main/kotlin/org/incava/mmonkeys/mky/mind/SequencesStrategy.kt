package org.incava.mmonkeys.mky.mind

import org.incava.mmonkeys.rand.Map3

object SequencesStrategy {
    fun getRandomChar(threes: Map3<Char, Int>, firstChar: Char, secondChar: Char): Char {
        val forFirst = threes.getValue(firstChar)
        return getRandomChar(forFirst, secondChar)
    }

    fun getRandomChar(mapping: Map<Char, Map<Char, *>>, firstChar: Char): Char {
        val forChar = mapping.getValue(firstChar)
        return getRandomChar(forChar)
    }

    fun getRandomChar(mapping: Map<Char, *>): Char {
        return mapping.keys.random()
    }
}