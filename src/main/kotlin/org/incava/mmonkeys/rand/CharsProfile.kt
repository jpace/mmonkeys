package org.incava.mmonkeys.rand

import org.incava.mmonkeys.chars.CharsElementFactory

class CharsProfile(from: Map<Char, Map<Char, Map<Char, Int>>>) {
    val firsts: CharsSlots
    val seconds: Map<Char, CharsSlots>
    val thirds: Map<Char, Map<Char, CharsSlots>>

    init {
        firsts = from.mapValues { (_, second) ->
            second.values.sumOf { thirds ->
                thirds.values.sum()
            }
        }.let { CharsSlots(it) }
        seconds = from.mapValues { (_, charToCharToCount) ->
            val asCharToLists = CharsElementFactory.toMapToList(charToCharToCount)
            CharSlotsFactory.createSlots(asCharToLists)
        }
        thirds = from.mapValues { (_, second) ->
            second.mapValues { (_, thirds) ->
                CharsSlots(thirds)
            }
        }
    }
}