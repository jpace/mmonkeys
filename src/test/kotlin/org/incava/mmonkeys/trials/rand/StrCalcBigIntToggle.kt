package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.mky.number.StringEncoderV3
import org.incava.mmonkeys.rand.RandBigInt
import org.incava.mmonkeys.trials.rand.StrRand.Constants.NUM_CHARS
import org.incava.rando.RndSlots
import java.math.BigInteger

class StrCalcBigIntToggle(private val slots: RndSlots) : StrRandSupplier {
    private val maxNumChars = 500
    private val ranges = mutableListOf<Pair<BigInteger, BigInteger>>()
    private val delegate = RandEncoded()

    init {
        val nChars = BigInteger.valueOf(NUM_CHARS.toLong())
        ranges += BigInteger.ZERO to RandBigInt.pow(BigInteger.valueOf(26), 1)
        (2 until maxNumChars).forEach {
            ranges += ranges.last().second to (ranges.last().second + RandBigInt.pow(nChars, it))
        }
    }

    private fun getString(length: Int): String {
        return if (length > 13) {
            val lowerLimit = ranges[length - 1].first
            val upperLimit = ranges[length - 1].second
            val rand = RandBigInt.rand(lowerLimit, upperLimit)
            StringEncoderV3.decode(rand)
        } else {
            val encoded = delegate.getEncoded(length)
            StringEncoderV3.decode(encoded)
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

    override fun doGet(length: Int): Any = getString(length)

    override fun doGet(length: Int, filter: GenFilter): Any? {
        TODO("Not yet implemented")
    }
}