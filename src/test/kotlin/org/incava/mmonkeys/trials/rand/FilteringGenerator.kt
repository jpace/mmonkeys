package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.type.Chars

class FilteringGenerator {
    fun getWord(numChars: Int, filter: Filtering): String? {
        val bytes = ByteArray(numChars)
        repeat(numChars) { index ->
            val n = Chars.randCharAz()
            val ch = 'a' + n
            val valid = filter.check(ch)
            if (!valid) {
                return null
            }
            bytes[index] = ch.toByte()
        }
        return String(bytes)
    }
}