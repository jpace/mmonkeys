package org.incava.mmonkeys.rand

import java.math.BigInteger

class RandBigInt2(private val upperLimit: BigInteger) {
    private val mask: Int
    private val bytes: ByteArray

    init {
        val numBits = upperLimit.bitLength()
        val numBytes = ((numBits.toLong() + 7) / 8).toInt()
        if (numBytes <= 0) {
            throw RuntimeException("numBytes $numBytes should not be less than zero")
        }
        val excessBits = 8 * numBytes - numBits
        mask = (1 shl 8 - excessBits) - 1
        bytes = ByteArray(numBytes)
    }

    fun rand(): BigInteger {
        while (true) {
            bytes[0] = (bytes[0].toInt() and mask - 1).toByte()
            val num = BigInteger(1, bytes)
            if (num < upperLimit) {
                return num
            }
        }
    }
}