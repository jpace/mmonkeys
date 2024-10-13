package org.incava.mmonkeys.trials.rand

import org.incava.rando.RandSlotsFactory
import org.incava.rando.RndSlots

object StrRandFactory {
    val genArray = RandSlotsFactory::genArray
    val genList = RandSlotsFactory::genList
    val genMap = RandSlotsFactory::genMap

    val calcArray = RandSlotsFactory::calcArray
    val calcList = RandSlotsFactory::calcList
    val calcMap = RandSlotsFactory::calcMap

    val build = StringFactory::buildString
    val buffer = StringFactory::bufferString
    val assemble = StringFactory::assembleString

    fun calcListBuild(): StrRand = create(calcList, build)

    fun create(slotsProvider: (Int, Int, Int) -> RndSlots, stringProvider: (Int, () -> Int) -> String): StrRand {
        val slots = slotsProvider(StrRand.Constants.NUM_CHARS + 1, 100, 10000)
        return object : StrLenRand(slots) {
            override fun getString(length: Int) = stringProvider(length) { (StrRand::randCharAz)(this) }
        }
    }
}