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

    fun getChar(thirds: Map<Char, Map<Char, CharDistRandom>>, firstChar: Char, secondChar: Char): Char {
        val forFirst = thirds.getValue(firstChar)
        return getChar(forFirst, secondChar)
    }

    fun <T> sumOfValues(map: Map<T, Int>): Int {
        return map.values.sum()
    }

    fun countOfFirsts3(threes: Map<Char, Map<Char, Map<Char, Int>>>): Map<Char, Int> {
        return threes.mapValues { (_, second) ->
            second.values.sumOf { thirds ->
                sumOfValues(thirds)
            }
        }
    }

    fun countOfSeconds3(threes: Map<Char, Map<Char, Map<Char, Int>>>): Map<Char, Map<Char, Int>> {
        return threes.mapValues { (_, second) ->
            countOfFirsts2(second)
        }
    }

    fun createFirsts3(threes: Map<Char, Map<Char, Map<Char, Int>>>): CharDistRandom {
        val counts = countOfFirsts3(threes)
        return DistributedRandom(counts)
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

    fun createSeconds3(threes: Map<Char, Map<Char, Map<Char, Int>>>): Map<Char, CharDistRandom> {
        val counts = countOfSeconds3(threes)
        return counts.mapValues { (_, seconds) -> DistributedRandom(seconds) }
    }

    fun createSeconds2(twos: Map<Char, Map<Char, Int>>): Map<Char, CharDistRandom> {
        return twos.mapValues { (_, second) ->
            DistributedRandom(second)
        }
    }

    fun createThirds3(threes: Map<Char, Map<Char, Map<Char, Int>>>): Map<Char, Map<Char, CharDistRandom>> {
        return threes.mapValues { (_, second) ->
            second.mapValues { (_, thirds) ->
                toDistributedRandom(thirds)
            }
        }
    }
}