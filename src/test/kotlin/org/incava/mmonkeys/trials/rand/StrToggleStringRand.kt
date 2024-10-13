package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.mky.number.StringEncoderV3
import org.incava.rando.RndSlots
import kotlin.random.Random

// Str Long, but StrRand if length > 13
class StrToggleStringRand(slots: RndSlots) : StrLenRand(slots) {
    var overruns = 0L
    val littleGen = RandEncoded()
    val bigGen = StrRandFactory.calcListBuild() as StrLenRand

    override fun randInt(limit: Int) = Random.nextInt(limit)

    override fun getString(length: Int): String {
        val encoded = littleGen.getEncoded(length)
        return StringEncoderV3.decode(encoded)
    }

    override fun get(): String {
        val len = randomLength()
        return if (len > 13) {
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
        } else if (len > 13) {
            bigGen.getString(filter)
        } else {
            getString(len)
        }
    }
}