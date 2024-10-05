package org.incava.mmonkeys.trials.rand

import org.incava.rando.RandSlotsFactory
import org.incava.rando.RndSlots

class StrLenRando(slots: RndSlots, val stringProvider: (Int, () -> Int) -> String, val charProvider: (StrRand) -> Int) :
    StrLenRand(slots) {
    override fun getString(length: Int) = stringProvider(length) { charProvider(this) }
}

object StrRandFactory {
    fun genMapBuild(): StrRand = create(RandSlotsFactory::genMap, StringFactory::buildString)

    fun genMapAssemble(): StrRand = create(RandSlotsFactory::genMap, StringFactory::assembleString)

    fun genMapBuffer(): StrRand = create(RandSlotsFactory::genMap, StringFactory::bufferString)

    fun genListBuild(): StrRand = create(RandSlotsFactory::genList, StringFactory::buildString)

    fun genListAssemble(): StrRand = create(RandSlotsFactory::genList, StringFactory::assembleString)

    fun genListBuffer(): StrRand = create(RandSlotsFactory::genList, StringFactory::bufferString)

    fun calcMapBuild(): StrRand = create(RandSlotsFactory::calcMap, StringFactory::buildString)

    fun calcMapAssemble(): StrRand = create(RandSlotsFactory::calcMap, StringFactory::assembleString)

    fun calcMapBuffer(): StrRand = create(RandSlotsFactory::calcMap, StringFactory::bufferString)

    fun calcListBuild(): StrRand = create(RandSlotsFactory::calcList, StringFactory::buildString)

    fun calcListAssemble(): StrRand = create(RandSlotsFactory::calcList, StringFactory::assembleString)

    fun calcListBuffer(): StrRand = create(RandSlotsFactory::calcList, StringFactory::bufferString)

    fun create(slotsProvider: (Int, Int, Int) -> RndSlots, stringProvider: (Int, () -> Int) -> String): StrRand {
        val slots = slotsProvider(StrRand.Constants.NUM_CHARS + 1, 100, 10000)
        return StrLenRando(slots, stringProvider, StrRand::randCharAz)
    }
}