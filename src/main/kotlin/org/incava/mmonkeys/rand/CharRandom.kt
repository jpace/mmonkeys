package org.incava.mmonkeys.rand

object CharRandom {
    fun getChar(seconds: Map<Char, CharsRandom>, firstChar: Char): Char {
        val forFirst = seconds.getValue(firstChar)
        return forFirst.nextDistributedRandom()
    }

    fun createFirsts2(twos: Map<Char, Map<Char, Int>>): CharsRandom {
        return twos.mapValues { (_, second) ->
            second.values.sum()
        }.let { CharsRandom(it) }
    }

    fun createSeconds2(twos: Map<Char, Map<Char, Int>>): Map<Char, CharsRandom> {
        return twos.mapValues { (_, second) ->
            CharsRandom(second)
        }
    }
}