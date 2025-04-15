package org.incava.mmonkeys.mky.corpus.dc

import org.incava.mmonkeys.mky.number.RandEncoded
import org.incava.mmonkeys.mky.number.StringEncoder
import org.incava.mmonkeys.words.Word

class EncodedGenerator(val corpus: DualCorpus) {
    fun getWord(numChars: Int): Word? {
        // this is essentially checkLength(numChars)
        val encoded = RandEncoded.random(numChars)
        return getMatch(numChars, encoded)
    }

    fun getMatch(numChars: Int, encoded: Long): Word? {
        val forLength = corpus.longsForLength(numChars) ?: return null
        val match = forLength[encoded]
        return if (match.isNullOrEmpty()) {
            null
        } else {
            val string = StringEncoder.decode(encoded)
            val index = corpus.setMatched(encoded, numChars)
            return Word(string, index)
        }
    }
}
