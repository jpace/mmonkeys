package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.mky.number.StringEncoderV3
import org.incava.mmonkeys.rand.RandBigInt
import org.incava.mmonkeys.trials.rand.StrRand.Constants.NUM_CHARS
import org.incava.rando.RndSlots
import java.math.BigInteger

class StrCalcBigIntOnly(private val slots: RndSlots) : StrLongRandSupplier {
    private val maxNumChars = 500
    private val ranges = mutableListOf<Pair<BigInteger, BigInteger>>()

    init {
        val nChars = BigInteger.valueOf(StrRandAlpha.Constants.NUM_CHARS.toLong())
        ranges += BigInteger.ZERO to RandBigInt.pow(BigInteger.valueOf(26), 1)
        (2 until maxNumChars).forEach {
            ranges += ranges.last().second to (ranges.last().second + RandBigInt.pow(nChars, it))
        }
    }

    private fun getString(length: Int): String {
        val lowerLimit = ranges[length - 1].first
        val upperLimit = ranges[length - 1].second
        val rand = RandBigInt.rand(lowerLimit, upperLimit)
        return StringEncoderV3.decode(rand)
    }

    override fun get(): String {
        val len = slots.nextInt()
        return getString(len)
    }

    override fun get(filter: Int): String {
        val len = slots.nextInt()
        return if (len > filter) "" else getString(len)
    }

    override fun doGet(length: Int): Any {
        return getString(length)
    }

    override fun doGet(length: Int, filter: GenFilter): Any? {
        TODO("Not yet implemented")
    }
}