package org.incava.mmonkeys.rand

typealias MapCharToCount = Map<Char, Int>
typealias MutableMapCharToCount = MutableMap<Char, Int>

object CharRandom {
    fun getChar(seconds: Map<Char, CharsSlots>, firstChar: Char): Char {
        val forFirst = seconds.getValue(firstChar)
        return forFirst.distributed.getChar()
    }

    fun createFirsts(twos: Map<Char, MapCharToCount>): CharsSlots {
        return twos.mapValues { (_, second) ->
            second.values.sum()
        }.let { CharsSlots(it) }
    }

    fun createSeconds(twos: Map<Char, MapCharToCount>): Map<Char, CharsSlots> {
        return twos.mapValues { (_, second) ->
            CharsSlots(second)
        }
    }
}