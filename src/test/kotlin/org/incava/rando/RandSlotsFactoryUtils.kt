package org.incava.rando

object RandSlotsFactoryUtils {
    private fun slotValues(obj: RndSlots, numSlots: Int): List<Int> {
        return (0 until numSlots)
            .map { obj.slotValue(it) }
            .toSortedSet()
            .toList()
    }

    fun generateNumbers(numSlots: Int, numChars: Int, numTrials: Int): Pair<List<Int>, List<Int>> {
        val gen = RandSlotsFactory.genMap(numChars, numSlots, numTrials)
        val calc = RandSlotsFactory.calcMap(numChars, numSlots, numTrials)

        val genNums = slotValues(gen, numSlots)
        val calcNums = slotValues(calc, numSlots)

        return genNums to calcNums
    }

    fun getGaps(x: List<Int>, y: List<Int>): Pair<Int, Int> {
        val firstGenGap = findGap(x) ?: -1
        val firstCalcGap = findGap(y) ?: -1
        return firstGenGap to firstCalcGap
    }

    fun getGaps(numSlots: Int, numChars: Int, numTrials: Int) : Pair<Int, Int> {
        val (genNums, calcNums) = generateNumbers(numSlots, numChars, numTrials)
        return getGaps(genNums, calcNums)
    }

    private fun findGap(collection: Collection<Int>): Int? {
        val unique = collection.sorted().distinct()
        val index = (0 until unique.size - 1).find { unique[it] + 1 != unique[it + 1] }
        return if (index == null) -1 else unique[index]
    }
}