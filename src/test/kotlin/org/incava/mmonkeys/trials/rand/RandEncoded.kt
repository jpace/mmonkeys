package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.mky.number.StringEncoderV3
import kotlin.random.Random

class RandEncoded {
    private val numChars = 26

    private val rangesEncoded = (1..13).associateWith { length ->
        val encoded = StringEncoderV3.encodeToLong("a".repeat(length))
        encoded to (encoded + 1) * numChars
    }

    fun randInt(limit: Int) = Random.nextInt(limit)

    fun getEncoded(length: Int): Long {
        val rangeEncoded = rangesEncoded[length] ?: return -1L
        val range = rangeEncoded.first * (numChars - 1) + numChars
        val randInRange = Random.nextLong(range)
        return rangeEncoded.first + randInRange
    }
}