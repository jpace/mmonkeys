package org.incava.mmonkeys.rand

typealias CharDistRandom = DistributedRandom<Char, Int>

object CharRandom {
    fun getChar(firsts: CharDistRandom): Char {
        return firsts.nextRandom()
    }

    fun getChar(seconds: Map<Char, CharDistRandom>, firstChar: Char): Char {
        val forFirst = seconds.getValue(firstChar)
        return getChar(forFirst)
    }

    fun createFirsts2(twos: Map<Char, Map<Char, Int>>): DistributedRandom<Char, Int> {
        return twos.mapValues { (_, second) ->
            second.values.sum()
        }.let { DistributedRandom(it) }
    }

    fun createSeconds2(twos: Map<Char, Map<Char, Int>>): Map<Char, CharDistRandom> {
        return twos.mapValues { (_, second) ->
            DistributedRandom(second)
        }
    }
}