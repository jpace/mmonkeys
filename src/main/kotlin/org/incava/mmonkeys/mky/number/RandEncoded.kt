package org.incava.mmonkeys.mky.number

import org.incava.mmonkeys.type.Chars
import kotlin.random.Random

object RandEncoded {
    object Constants {
        // number of characters that can fit into a long (with "crpxnlskvljfhh" at 14 characters, Long.MAX_VALUE)
        const val MAX_CHARS: Int = 13
    }

    private val rangesEncoded = (1..Constants.MAX_CHARS).associateWith { length ->
        val encoded = StringEncoder.encodeToLong("a".repeat(length))
        encoded to (encoded + 1) * Chars.NUM_ALPHA_CHARS
    }

    fun random(length: Int): Long {
        val rangeEncoded = rangesEncoded[length] ?: throw IllegalArgumentException("invalid length: $length")
        // range = (x + 1) * 26 - x
        //  step 1: 26x + 26 - x
        //  step 2: 25x + 26
        //  step 3: profit!
        val range = rangeEncoded.first * (Chars.NUM_ALPHA_CHARS - 1) + Chars.NUM_ALPHA_CHARS
        val randInRange = Random.nextLong(range)
        return rangeEncoded.first + randInRange
    }
}