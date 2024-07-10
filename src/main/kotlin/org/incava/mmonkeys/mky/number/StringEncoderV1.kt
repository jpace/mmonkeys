package org.incava.mmonkeys.mky.number

object StringEncoderV1 {
    // a .. z (no numbers yet)
    private const val NUM_CHARS = 26

    fun encodeToInt(string: String): Int {
        var value = 1
        string.toCharArray().forEach {
            value *= NUM_CHARS
            value += it - 'a'
        }
        return value
    }

    fun encodeToLong(string: String): Long {
        var value = 1L
        string.toCharArray().forEach {
            value *= NUM_CHARS.toLong()
            value += it - 'a'
        }
        return value
    }

    fun encode(string: String): Number {
        return if (string.length <= 6) encodeToInt(string) else encodeToLong(string)
    }

    fun decode(number: Int): String {
        val sb = StringBuilder()
        var value = number
        while (true) {
            val x = value % NUM_CHARS
            value /= NUM_CHARS
            if (value == 0) {
                break
            } else {
                val ch = 'a' + x
                sb.insert(0, ch)
            }
        }
        return sb.toString()
    }

    fun decode(number: Long): String {
        val sb = StringBuilder()
        var value = number
        while (true) {
            val x = (value % NUM_CHARS).toInt()
            value /= NUM_CHARS
            if (value == 0L) {
                break
            } else {
                val ch = 'a' + x
                sb.insert(0, ch)
            }
        }
        return sb.toString()
    }
}