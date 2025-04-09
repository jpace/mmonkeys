package org.incava.mmonkeys.rand

object Char3Random {
    fun getChar(thirds: Map<Char, Map<Char, CharDistRandom>>, firstChar: Char, secondChar: Char): Char {
        val forFirst = thirds.getValue(firstChar)
        return CharRandom.getChar(forFirst, secondChar)
    }

    fun countOfFirsts3(threes: Map<Char, Map<Char, Map<Char, Int>>>): Map<Char, Int> {
        return threes.mapValues { (_, second) ->
            second.values.sumOf { thirds ->
                CharRandom.sumOfValues(thirds)
            }
        }
    }

    fun countOfSeconds3(threes: Map<Char, Map<Char, Map<Char, Int>>>): Map<Char, Map<Char, Int>> {
        return threes.mapValues { (_, second) ->
            CharRandom.countOfFirsts2(second)
        }
    }

    fun createFirsts3(threes: Map<Char, Map<Char, Map<Char, Int>>>): CharDistRandom {
        val counts = countOfFirsts3(threes)
        return DistributedRandom(counts)
    }

    fun createSeconds3(threes: Map<Char, Map<Char, Map<Char, Int>>>): Map<Char, CharDistRandom> {
        val counts = countOfSeconds3(threes)
        return counts.mapValues { (_, seconds) -> DistributedRandom(seconds) }
    }

    fun createThirds3(threes: Map<Char, Map<Char, Map<Char, Int>>>): Map<Char, Map<Char, CharDistRandom>> {
        return threes.mapValues { (_, second) ->
            second.mapValues { (_, thirds) ->
                CharRandom.toDistributedRandom(thirds)
            }
        }
    }
}