package org.incava.mmonkeys.mky.number

object StringEncoderV2 {
    // a .. z (no numbers yet)
    private const val NUM_CHARS = 26

    fun encodeToInt(str: String): Int {
        return if (str.isBlank()) -1 else str.toCharArray().fold(0) { value, ch -> value * 27 + getOffset(ch) }
    }

    fun encodeToLong(str: String): Long {
        return if (str.isBlank()) return -1L else str.toCharArray()
            .fold(0L) { value, ch -> value * 27L + getOffset(ch) }
    }

    fun decode(encoded: Int): String {
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

    fun decode(encoded: Long): String {
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