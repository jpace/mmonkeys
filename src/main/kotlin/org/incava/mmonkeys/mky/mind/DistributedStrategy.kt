package org.incava.mmonkeys.mky.mind

import org.incava.mmonkeys.rand.DistributedRandom

object DistributedStrategy {
    fun <T> sumOfValues(map: Map<T, Int>): Int {
        return map.values.sum()
    }

    fun getChar(firsts: DistributedRandom<Char, Int>): Char {
        return firsts.nextRandom()
    }

    fun getChar(seconds: Map<Char, DistributedRandom<Char, Int>>, firstChar: Char): Char {
        val forFirst = seconds.getValue(firstChar)
        return getChar(forFirst)
    }

    fun getChar(thirds: Map<Char, Map<Char, DistributedRandom<Char, Int>>>, firstChar: Char, secondChar: Char): Char {
        val forFirst = thirds.getValue(firstChar)
        return getChar(forFirst, secondChar)
    }

    fun createFirsts3(threes: Map<Char, Map<Char, Map<Char, Int>>>): DistributedRandom<Char, Int> {
        return threes.mapValues { (_, second) ->
            second.values.sumOf { thirds ->
                sumOfValues(thirds)
            }
        }.let { DistributedRandom(it) }
    }

    fun createFirsts2(twos: Map<Char, Map<Char, Int>>): DistributedRandom<Char, Int> {
        return twos.mapValues { (_, second) ->
            sumOfValues(second)
        }.let { DistributedRandom(it) }
    }

    fun createSeconds3(threes: Map<Char, Map<Char, Map<Char, Int>>>): Map<Char, DistributedRandom<Char, Int>> {
        return threes.mapValues { (_, second) ->
            second.mapValues { (_, thirds) ->
                sumOfValues(thirds)
            }.let { DistributedRandom(it) }
        }
    }

    fun createSeconds2(twos: Map<Char, Map<Char, Int>>): Map<Char, DistributedRandom<Char, Int>> {
        return twos.mapValues { (_, second) ->
            DistributedRandom(second)
        }
    }

    fun createThirds3(threes: Map<Char, Map<Char, Map<Char, Int>>>): Map<Char, Map<Char, DistributedRandom<Char, Int>>> {
        return threes.mapValues { (_, second) ->
            second.mapValues { (_, thirds) ->
                DistributedRandom(thirds)
            }
        }
    }
}