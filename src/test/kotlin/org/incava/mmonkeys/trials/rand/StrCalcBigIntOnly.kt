package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.mky.number.StringEncoderV3
import org.incava.mmonkeys.rand.RandBigInt
import org.incava.mmonkeys.trials.rand.StrRand.Constants.NUM_CHARS
import org.incava.rando.RandCalcList
import java.math.BigInteger

class StrCalcBigIntOnly : StrRand() {
    private val lengthRand = RandCalcList(NUM_CHARS + 1, 100, 10000)
    private val maxNumChars = 500
    val ranges = mutableListOf<Pair<BigInteger, BigInteger>>()

    init {
        val nChars = BigInteger.valueOf(NUM_CHARS.toLong())
        ranges += BigInteger.ZERO to RandBigInt.pow(BigInteger.valueOf(26), 1)
        (2 until maxNumChars).forEach {
            ranges += ranges.last().second to (ranges.last().second + RandBigInt.pow(nChars, it))
        }
    }

    override fun randInt(limit: Int): Int {
        TODO("Not used")
    }

    private fun getString(length: Int): String {
        val lowerLimit = ranges[length - 1].first
        val upperLimit = ranges[length - 1].second
        val rand = RandBigInt.rand(lowerLimit, upperLimit)
        return StringEncoderV3.decode(rand)
    }

    override fun get(): String {
        val len = lengthRand.nextInt()
        return getString(len)
    }

    override fun get(filter: Int): String {
        val len = lengthRand.nextInt()
        return if (len > filter) "" else getString(len)
    }
}