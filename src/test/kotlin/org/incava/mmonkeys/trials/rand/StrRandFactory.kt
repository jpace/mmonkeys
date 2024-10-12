package org.incava.mmonkeys.trials.rand

import org.incava.rando.RandSlotsFactory
import org.incava.rando.RndSlots

object StrRandFactory {
    val genList = RandSlotsFactory::genList
    val genMap = RandSlotsFactory::genMap
    val calcList = RandSlotsFactory::calcList
    val calcMap = RandSlotsFactory::calcMap
    val build = StringFactory::buildString
    val buffer = StringFactory::bufferString
    val assemble = StringFactory::assembleString

    fun genMapBuild(): StrRand = create(genMap, build)

    fun genMapAssemble(): StrRand = create(genMap, assemble)

    fun genMapBuffer(): StrRand = create(genMap, buffer)

    fun genListBuild(): StrRand = create(genList, build)

    fun genListAssemble(): StrRand = create(genList, assemble)

    fun genListBuffer(): StrRand = create(genList, buffer)

    fun calcMapBuild(): StrRand = create(calcMap, build)

    fun calcMapAssemble(): StrRand = create(calcMap, assemble)

    fun calcMapBuffer(): StrRand = create(calcMap, buffer)

    fun calcListBuild(): StrRand = create(calcList, build)

    fun calcListAssemble(): StrRand = create(calcList, assemble)

    fun calcListBuffer(): StrRand = create(calcList, buffer)

    fun create(slotsProvider: (Int, Int, Int) -> RndSlots, stringProvider: (Int, () -> Int) -> String): StrRand {
        val slots = slotsProvider(StrRand.Constants.NUM_CHARS + 1, 100, 10000)
        return object : StrLenRand(slots) {
            override fun getString(length: Int) = stringProvider(length) { (StrRand::randCharAz)(this) }
        }
    }
}