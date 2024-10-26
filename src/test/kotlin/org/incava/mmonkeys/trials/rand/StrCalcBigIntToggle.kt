package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.mky.number.StringEncoder
import org.incava.mmonkeys.rand.RandBigInt
import org.incava.mmonkeys.type.Chars
import org.incava.rando.RndSlots
import java.math.BigInteger

class StrCalcBigIntToggle(private val slots: RndSlots) : StrSupplier {
    private val maxNumChars = 500
    private val ranges = mutableListOf<Pair<BigInteger, BigInteger>>()
    private val delegate = RandEncoded()

    init {
        val nChars = BigInteger.valueOf(Chars.NUM_ALPHA_CHARS.toLong())
        ranges += BigInteger.ZERO to RandBigInt.pow(nChars, 1)
        (2 until maxNumChars).forEach {
            ranges += ranges.last().second to (ranges.last().second + RandBigInt.pow(nChars, it))
        }
    }

    private fun getString(length: Int): String {
        return if (length > Chars.NUM_ALPHA_CHARS) {
            val lowerLimit = ranges[length - 1].first
            val upperLimit = ranges[length - 1].second
            val rand = RandBigInt.rand(lowerLimit, upperLimit)
            StringEncoder.decode(rand)
        } else {
            val encoded = delegate.getEncoded(length)
            StringEncoder.decode(encoded)
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