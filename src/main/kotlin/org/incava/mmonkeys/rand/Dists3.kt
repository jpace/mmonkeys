package org.incava.mmonkeys.rand

class Dists3(from: LinkedHashMap<Char, MutableMap<Char, MutableMap<Char, Int>>>) {
    val firsts: CharsRandom
    val seconds: Map<Char, CharsRandom>
    val thirds: Map<Char, Map<Char, CharsRandom>>

    init {
        firsts = from.mapValues { (_, second) ->
            second.values.sumOf { thirds ->
                thirds.values.sum()
            }
        }.let { CharsRandom(it) }
        seconds = from.mapValues { (_, second) ->
            CharRandom.createFirsts2(second)
        }
        thirds = from.mapValues { (_, second) ->
            second.mapValues { (_, thirds) ->
                CharsRandom(thirds)
            }
        }
    }

    fun nextDistributedChar(firstChar: Char, secondChar: Char): Char {
        val forFirst = thirds[firstChar] ?: return firsts.nextRealRandom()
        return CharRandom.getChar(forFirst, secondChar)
    }

    fun nextDistributedChar(firstChar: Char): Char = CharRandom.getChar(seconds, firstChar)

    fun nextDistributedChar(): Char = firsts.nextDistributedRandom()

    fun nextRandomChar(): Char = firsts.nextRealRandom()

    fun nextRandomChar(firstChar: Char): Char {
        val forChar = seconds[firstChar] ?: return firsts.nextRealRandom()
        return forChar.nextRealRandom()
    }

    fun nextRandomChar(firstChar: Char, secondChar: Char): Char {
        val forFirstChar = thirds[firstChar] ?: return firsts.nextRealRandom()
        val forSecondChar = forFirstChar[secondChar] ?: return firsts.nextRealRandom()
        return forSecondChar.nextRealRandom()
    }
}