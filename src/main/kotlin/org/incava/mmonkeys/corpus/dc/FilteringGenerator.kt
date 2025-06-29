package org.incava.mmonkeys.corpus.dc

import org.incava.mmonkeys.type.Chars

class FilteringGenerator(val filterSupplier: (Int) -> LengthFilter) {
    fun getRandomString(numChars: Int): String? {
        val filter = filterSupplier(numChars)
        if (!filter.hasCandidates()) {
            return null
        }
        val bytes = ByteArray(numChars)
        repeat(numChars) { index ->
            val n = Chars.randCharAz()
            val ch = 'a' + n
            val valid = filter.check(ch)
            if (!valid) {
                return null
            }
            bytes[index] = ch.code.toByte()
        }
        return String(bytes)
    }
}