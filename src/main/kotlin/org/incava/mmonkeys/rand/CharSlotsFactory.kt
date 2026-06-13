package org.incava.mmonkeys.rand

typealias MapCharToCount = Map<Char, Int>
typealias MutableMapCharToCount = MutableMap<Char, Int>

object CharSlotsFactory {
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