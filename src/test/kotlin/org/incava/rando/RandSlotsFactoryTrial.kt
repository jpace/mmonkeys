package org.incava.rando

import org.incava.ikdk.io.Console

class RandSlotsFactoryTrial {
    fun calcSlots() {
        val result = RandSlotsFactory.calcSlots(27, 100, 10_000_000).values.toList()
        Console.info("result", result)
    }

    fun showGap(numSlots: Int, numChars: Int, numTrials: Int) {
        val (genNums, calcNums) = RandSlotsFactoryUtils.generateNumbers(numSlots, numChars, numTrials)
        val (genGap, calcGap) = RandSlotsFactoryUtils.getGaps(genNums, calcNums)

        println("# chars : $numChars")
        println("# slots : $numSlots")
        println("gen numbers  : $genNums")
        println("calc numbers : $calcNums")
        println("gen gap  : $genGap")
        println("calc gap : $calcGap")
    }
}

fun main() {
    val obj = RandSlotsFactoryTrial()
    obj.calcSlots()
    // gaps should be at least the length of the longest string "honorificabilitudinitatibus"
    // gap at ~41
    obj.showGap(100, 27, 10_000_000)
    // gap at ~29
    obj.showGap(64, 27, 10_000_000)
    // gap at ~44
    obj.showGap(100, 30, 100_000_000)
}