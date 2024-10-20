package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.mky.number.StringEncoderV3
import kotlin.random.Random

class RandEncoded {
    object Constants {
        // number of characters that can fit into a long (with "crpxnlskvljfhh" at 14 characters, Long.MAX_VALUE)
        const val MAX_CHARS: Int = 13
    }

    private val rangesEncoded = (1..Constants.MAX_CHARS).associateWith { length ->
        val encoded = StringEncoderV3.encodeToLong("a".repeat(length))
        encoded to (encoded + 1) * StrRandAlpha.Constants.NUM_CHARS
    }

    fun getEncoded(length: Int): Long {
        val rangeEncoded = rangesEncoded[length] ?: return -1L
        val range = rangeEncoded.first * (StrRandAlpha.Constants.NUM_CHARS - 1) + StrRandAlpha.Constants.NUM_CHARS
        val randInRange = Random.nextLong(range)
        return rangeEncoded.first + randInRange
    }
}