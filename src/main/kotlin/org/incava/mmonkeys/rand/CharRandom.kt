package org.incava.mmonkeys.rand

typealias CharDistRandom = DistributedRandom<Char, Int>

object CharRandom {
    fun toDistributedRandom(chars: Map<Char, Int>): CharDistRandom {
        return DistributedRandom(chars)
    }

    fun getChar(firsts: CharDistRandom): Char {
        return firsts.nextRandom()
    }

    fun getChar(seconds: Map<Char, CharDistRandom>, firstChar: Char): Char {
        val forFirst = seconds.getValue(firstChar)
        return getChar(forFirst)
    }

    fun <T> sumOfValues(map: Map<T, Int>): Int {
        return map.values.sum()
    }

    fun countOfFirsts2(twos: Map<Char, Map<Char, Int>>): Map<Char, Int> {
        return twos.mapValues { (_, second) ->
            sumOfValues(second)
        }
    }

    fun createFirsts2(twos: Map<Char, Map<Char, Int>>): CharDistRandom {
        val counts = countOfFirsts2(twos)
        return DistributedRandom(counts)
    }

    fun createSeconds2(twos: Map<Char, Map<Char, Int>>): Map<Char, CharDistRandom> {
        return twos.mapValues { (_, second) ->
            DistributedRandom(second)
        }
    }
}