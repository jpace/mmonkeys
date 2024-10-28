package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.mky.corpus.DualCorpus
import org.incava.mmonkeys.mky.corpus.MapCorpus
import org.incava.mmonkeys.type.Chars

class FilteringGenerator(val corpus: DualCorpus) {
    fun getWord(numChars: Int, filter: LengthFiltering): String? {
        if (!filter.checkLength()) {
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
            bytes[index] = ch.toByte()
        }
        return String(bytes).also { word ->
            corpus.matched(word, numChars)
        }
    }
}