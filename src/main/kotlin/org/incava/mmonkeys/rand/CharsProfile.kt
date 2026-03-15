package org.incava.mmonkeys.rand

class CharsProfile(from: Map<Char, Map<Char, MapCharToCount>>) {
    val random: CharsRandomProfile
    val dist: CharsDistProfile

    init {
        val firsts = from.mapValues { (_, second) ->
            second.values.sumOf { thirds ->
                thirds.values.sum()
            }
        }.let { CharsSlots(it) }
        val seconds = from.mapValues { (_, second) ->
            CharRandom.createFirsts(second)
        }
        val thirds = from.mapValues { (_, second) ->
            second.mapValues { (_, thirds) ->
                CharsSlots(thirds)
            }
        }
        random = CharsRandomProfile(firsts, seconds, thirds)
        dist = CharsDistProfile(firsts, seconds, thirds)
    }
}