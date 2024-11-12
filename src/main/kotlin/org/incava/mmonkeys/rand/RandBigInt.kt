package org.incava.mmonkeys.rand

import org.incava.ikdk.io.Console
import org.incava.ikdk.math.Maths
import java.math.BigInteger

object RandBigInt {
    private val jdkRandom = java.util.Random()

    fun pow(base: BigInteger, exponent: Int) = Maths.powerBigRepeat(base, exponent)

    fun rand(upperLimit: BigInteger): BigInteger {
        val numBits = upperLimit.bitLength()
        Console.info("numBits", numBits)
        Console.info("numBits + 7", numBits + 7)
        val numBytes = ((numBits.toLong() + 7) / 8).toInt()
        Console.info("numBytes", numBytes)
        val numBytes2 = (numBits + 7) / 8
        Console.info("numBytes2", numBytes2)
        val excessBits = 8 * numBytes - numBits
        Console.info("excessBits", excessBits)

        while (true) {
            val num = BigInteger(numBits, jdkRandom)
            if (num < upperLimit) {
                return num
            }
            Console.info("num", commify(num))
        }
    }

    fun rand(lowerLimit: BigInteger, upperLimit: BigInteger): BigInteger {
        val diff = upperLimit - lowerLimit
        val rnd = rand(diff)
        return lowerLimit + rnd
    }

    fun commify(number: Number) : String {
        return number.toString().reversed().replace(Regex("(\\d\\d\\d)(?=\\d)"), "$1,").reversed()
    }
}