package org.incava.mmonkeys.rand

object Char3Random {
    fun getChar(thirds: Map<Char, Map<Char, CharDistRandom>>, firstChar: Char, secondChar: Char): Char {
        val forFirst = thirds.getValue(firstChar)
        return CharRandom.getChar(forFirst, secondChar)
    }

    fun createFirsts3(threes: Map3<Char, Int>): CharDistRandom {
        return threes.mapValues { (_, second) ->
            second.values.sumOf { thirds ->
                thirds.values.sum()
            }
        }.let { DistributedRandom(it) }
    }

    fun createSeconds3(threes: Map3<Char, Int>): Map<Char, CharDistRandom> {
        return threes.mapValues { (_, second) ->
            CharRandom.createFirsts2(second)
        }
    }

    fun createThirds3(threes: Map3<Char, Int>): Map<Char, Map<Char, CharDistRandom>> {
        return threes.mapValues { (_, second) ->
            second.mapValues { (_, thirds) ->
                DistributedRandom(thirds)
            }
        }
    }
}