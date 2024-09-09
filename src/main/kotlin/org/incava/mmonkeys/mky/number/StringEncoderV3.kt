package org.incava.mmonkeys.mky.number

import java.math.BigInteger

object StringEncoderV3 {
    // a .. z (no numbers yet)
    private const val NUM_CHARS = 26

    fun encodeToInt(string: String): Int {
        return if (string.isBlank())
            -1
        else
            string.toCharArray().fold(-1) { value, ch ->
                val num = ch - 'a'
                (value + 1) * NUM_CHARS + num
            }
    }

    fun encodeToLong(string: String): Long {
        return if (string.isBlank())
            -1L
        else
            string.toCharArray().fold(-1L) { value, ch ->
                val num = ch - 'a'
                (value + 1L) * NUM_CHARS + num
            }
    }

    fun encode(string: String): Number {
        return if (string.length <= 6) encodeToInt(string) else encodeToLong(string)
    }

    fun decode(number: Int): String {
        val sb = StringBuilder()
        var value = number
        val x = value % NUM_CHARS
        value /= NUM_CHARS
        sb.insert(0, 'a' + x)
        while (value > 0) {
            val y = value % NUM_CHARS
            value /= NUM_CHARS
            if (y == 0) {
                sb.insert(0, 'z')
                value--
            } else {
                sb.insert(0, 'a' + y - 1)
            }
        }
        return sb.toString()
    }

    fun decode(number: Long): String {
        val sb = StringBuilder()
        var value = number
        val x = (value % NUM_CHARS).toInt()
        value /= NUM_CHARS
        sb.insert(0, 'a' + x)
        while (value > 0L) {
            val y = (value % NUM_CHARS).toInt()
            value /= NUM_CHARS
            if (y == 0) {
                sb.insert(0, 'z')
                value--
            } else {
                sb.insert(0, 'a' + y - 1)
            }
        }
        return sb.toString()
    }

    fun decode(number: BigInteger): String {
        val sb = StringBuilder()
        var value = number
        val x = (value.mod(NUM_CHARS.toBigInteger())).toInt()
        value = value.divide(NUM_CHARS.toBigInteger())
        sb.insert(0, 'a' + x)
        while (value > BigInteger.ZERO) {
            val y = (value.mod(NUM_CHARS.toBigInteger())).toInt()
            value = value.divide(NUM_CHARS.toBigInteger())
            if (y == 0) {
                sb.insert(0, 'z')
                value--
            } else {
                sb.insert(0, 'a' + y - 1)
            }
        }
        return sb.toString()
    }
}