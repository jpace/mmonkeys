package org.incava.mmonkeys.rand

class Dists3(from: LinkedHashMap<Char, MutableMap<Char, MutableMap<Char, Int>>>) {
    val firsts: CharDistRandom
    val seconds: Map<Char, CharDistRandom>
    val thirds: Map<Char, Map<Char, CharDistRandom>>

    init {
        firsts = from.mapValues { (_, second) ->
            second.values.sumOf { thirds ->
                thirds.values.sum()
            }
        }.let { DistributedRandom(it) }
        seconds = from.mapValues { (_, second) ->
            CharRandom.createFirsts2(second)
        }
        thirds = from.mapValues { (_, second) ->
            second.mapValues { (_, thirds) ->
                DistributedRandom(thirds)
            }
        }
    }

    fun getChar(firstChar: Char, secondChar: Char): Char {
        val forFirst = thirds.getValue(firstChar)
        return CharRandom.getChar(forFirst, secondChar)
    }

    fun getChar(firstChar: Char): Char = CharRandom.getChar(seconds, firstChar)

    fun getChar(): Char = CharRandom.getChar(firsts)
}