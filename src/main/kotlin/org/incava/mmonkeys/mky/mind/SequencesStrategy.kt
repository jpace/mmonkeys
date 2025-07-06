package org.incava.mmonkeys.mky.mind

object SequencesStrategy {

    fun getRandomChar(mapping: Map<Char, Map<Char, *>>, firstChar: Char): Char {
        val forChar = mapping.getValue(firstChar)
        return getRandomChar(forChar)
    }

    fun getRandomChar(mapping: Map<Char, *>): Char {
        return mapping.keys.random()
    }
}