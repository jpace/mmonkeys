package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.mky.number.StringEncoderV3
import org.incava.mmonkeys.trials.rand.StrRand.Constants.NUM_CHARS
import org.incava.rando.RandSlotsFactory
import kotlin.random.Random

// Str Long, but StrRand if length > 13
class StrToggleAnyRand : StrLenRand(RandSlotsFactory.calcList(NUM_CHARS + 1, 100, 10000)) {
    var overruns = 0L
    private val rangesEncoded = (1..13).associateWith { length ->
        val encoded = StringEncoderV3.encodeToLong("a".repeat(length))
        encoded to (encoded + 1) * 26
    }
    val bigGen = StrRandFactory.calcListBuild() as StrLenRand

    override fun randInt(limit: Int) = Random.nextInt(limit)

    override fun getString(length: Int): String {
        val rangeEncoded = rangesEncoded[length] ?: return "??!"
        val range = rangeEncoded.first * 25 + 26
        val randInRange = Random.nextLong(range)
        val encoded = rangeEncoded.first + randInRange
        // no decoding
        return ""
    }

    override fun get(): String {
        val len = randomLength()
        if (len > 13) {
            ++overruns
            return bigGen.get()
        }
        return getString(len)
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