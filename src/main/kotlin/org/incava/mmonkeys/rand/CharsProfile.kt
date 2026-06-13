package org.incava.mmonkeys.rand

class CharsProfile(from: Map<Char, Map<Char, MapCharToCount>>) {
    val firsts: CharsSlots
    val seconds: Map<Char, CharsSlots>
    val thirds: Map<Char, Map<Char, CharsSlots>>

    init {
        firsts = from.mapValues { (_, second) ->
            second.values.sumOf { thirds ->
                thirds.values.sum()
            }
        }.let { CharsSlots(it) }
        seconds = from.mapValues { (_, second) ->
            CharSlotsFactory.createFirsts(second)
        }
        thirds = from.mapValues { (_, second) ->
            second.mapValues { (_, thirds) ->
                CharsSlots(thirds)
            }
        }
    }
}