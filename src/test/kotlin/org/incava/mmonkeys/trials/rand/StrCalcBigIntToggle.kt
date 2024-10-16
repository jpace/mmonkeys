package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.mky.number.StringEncoderV3
import org.incava.mmonkeys.rand.RandBigInt
import org.incava.rando.RndSlots
import java.math.BigInteger

class StrCalcBigIntToggle(val slots: RndSlots) : StrRand() {
    private val maxNumChars = 500
    val ranges = mutableListOf<Pair<BigInteger, BigInteger>>()
    val longDecoder = StrRandDecoded(slots)
    var filtered = 0L

    init {
        val nChars = BigInteger.valueOf(Constants.NUM_CHARS.toLong())
        ranges += BigInteger.ZERO to RandBigInt.pow(BigInteger.valueOf(26), 1)
        (2 until maxNumChars).forEach {
            ranges += ranges.last().second to (ranges.last().second + RandBigInt.pow(nChars, it))
        }
    }

    override fun randInt(limit: Int): Int {
        TODO("Not used")
    }

    fun getString(length: Int): String {
        return if (length > 13) {
            val lowerLimit = ranges[length - 1].first
            val upperLimit = ranges[length - 1].second
            val rand = RandBigInt.rand(lowerLimit, upperLimit)
            StringEncoderV3.decode(rand)
        } else {
            longDecoder.getString(length)
        }
    }

    override fun get(): String {
        val len = slots.nextInt()
        return getString(len)
    }

    override fun get(filter: Int): String {
        val len = slots.nextInt()
        return if (len > filter) "" else getString(len)
    }
}