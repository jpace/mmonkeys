package org.incava.mmonkeys.mky.mind

import org.incava.mmonkeys.rand.DistributedRandom

object DistributedStrategy {
    fun <T> sumOfValues(map: Map<T, Int>): Int {
        return map.values.sum()
    }

    fun getFirstChar(firsts: DistributedRandom<Char, Int>): Char {
        return firsts.nextRandom()
    }

    fun getChar(seconds: Map<Char, DistributedRandom<Char, Int>>, firstChar: Char): Char {
        return seconds.getValue(firstChar).nextRandom()
    }
}