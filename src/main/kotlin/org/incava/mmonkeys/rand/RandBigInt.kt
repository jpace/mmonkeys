package org.incava.mmonkeys.rand

import org.incava.ikdk.math.Maths
import java.math.BigInteger

object RandBigInt {
    private val jdkRandom = java.util.Random()

    fun pow(base: BigInteger, exponent: Int) = Maths.powerBigRepeat(base, exponent)

    fun rand(upperLimit: BigInteger): BigInteger {
        val numBits = upperLimit.bitLength()
        while (true) {
            val num = BigInteger(numBits, jdkRandom)
            if (num < upperLimit)
                return num
        }
    }

    fun rand(lowerLimit: BigInteger, upperLimit: BigInteger): BigInteger {
        val diff = upperLimit - lowerLimit
        val rnd = rand(diff)
        return lowerLimit + rnd
    }
}