package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.mky.number.StringEncoderV3
import org.incava.rando.RndSlots
import kotlin.random.Random

// Str Long, but StrRand if length > 13
class StrToggleStringRand(slots: RndSlots) : StrLenRand(slots) {
    private var overruns = 0L
    private val littleGen = RandEncoded()
    private val bigGen = StrRandFactory.create(slots.numSlots, StrRandFactory.calcArray, StrRandFactory.assemble) as StrLenRand

    override fun getString(length: Int): String {
        val encoded = littleGen.getEncoded(length)
        return StringEncoderV3.decode(encoded)
    }

    override fun get(): String {
        val len = randomLength()
        return if (len > RandEncoded.Constants.MAX_CHARS) {
            ++overruns
            bigGen.get(len)
        } else {
            getString(len)
        }
    }

    override fun get(filter: Int): String {
        val len = randomLength()
        return if (len > filter) {
            ""
        } else if (len > RandEncoded.Constants.MAX_CHARS) {
            bigGen.getString(len)
        } else {
            getString(len)
        }
    }
}