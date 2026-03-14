package org.incava.mmonkeys.rand

typealias MapCharToCount = Map<Char, Int>
typealias MutableMapCharToCount = MutableMap<Char, Int>

object CharRandom {
    fun getChar(seconds: Map<Char, CharsRandom>, firstChar: Char): Char {
        val forFirst = seconds.getValue(firstChar)
        return forFirst.nextDistributedRandom()
    }

    fun createFirsts2(twos: Map<Char, MapCharToCount>): CharsRandom {
        return twos.mapValues { (_, second) ->
            second.values.sum()
        }.let { CharsRandom(it) }
    }

    fun createSeconds2(twos: Map<Char, MapCharToCount>): Map<Char, CharsRandom> {
        return twos.mapValues { (_, second) ->
            CharsRandom(second)
        }
    }
}