package org.incava.mmonkeys.mky.mind

object SequencesStrategy {
    fun <T> getRandomChar(threes: Map<Char, Map<Char, Map<Char, T>>>, firstChar: Char, secondChar: Char): Char {
        val forFirst = threes.getValue(firstChar)
        return getRandomChar(forFirst, secondChar)
    }

    fun <T> getRandomChar(mapping: Map<Char, Map<Char, T>>, firstChar: Char): Char {
        val forChar = mapping.getValue(firstChar)
        return getRandomChar(forChar)
    }

    fun <T> getRandomChar(mapping: Map<Char, T>): Char {
        return mapping.keys.random()
    }
}