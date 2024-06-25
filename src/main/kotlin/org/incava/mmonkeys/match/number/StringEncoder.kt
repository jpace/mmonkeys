package org.incava.mmonkeys.match.number

object StringEncoder {
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

    fun encodeToBase27(str: String): Int {
        return if (str.isBlank()) -1 else str.toCharArray().fold(0) { value, ch -> value * 27 + getOffset(ch) }
    }

    fun encodeToBase27Long(str: String): Long {
        return if (str.isBlank()) return -1L else str.toCharArray()
            .fold(0L) { value, ch -> value * 27L + getOffset(ch) }
    }

    fun decodeFromBase27(encoded: Int): String {
        val sb = StringBuilder()
        val v1 = encoded % 27
        sb.append('a' + v1 - 1)
        var v2 = encoded / 27
        while (v2 > 0) {
            sb.insert(0, 'a' + (v2 % 27) - 1)
            v2 /= 27
        }
        return sb.toString()
    }

    fun decodeFromBase27(encoded: Long): String {
        val sb = StringBuilder()
        val v1 = (encoded % 27L).toInt()
        sb.append('a' + v1 - 1)
        var v2 = encoded / 27
        while (v2 > 0) {
            sb.insert(0, 'a' + (v2 % 27).toInt() - 1)
            v2 /= 27
        }
        return sb.toString()
    }

    private fun getOffset(char: Char): Int = char + 1 - 'a'
}