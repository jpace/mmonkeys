package org.incava.mmonkeys.mky.corpus.dc

import org.incava.mmonkeys.type.Chars
import org.incava.mmonkeys.words.Word

class FilteringGenerator(val corpus: DualCorpus, val filterSupplier: (Int) -> LengthFilter) {
    fun getWord(numChars: Int): Word? {
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
        val string = String(bytes)
        val index = corpus.setMatched(string, numChars)
        return Word(string, index)
    }
}