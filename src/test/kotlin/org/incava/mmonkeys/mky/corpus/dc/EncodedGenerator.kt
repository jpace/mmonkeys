package org.incava.mmonkeys.mky.corpus.dc

import org.incava.mmonkeys.mky.number.RandEncoded
import org.incava.mmonkeys.mky.number.StringEncoder
import org.incava.mmonkeys.words.Word

class EncodedGenerator(val corpus: DualCorpus) {
    fun getWord(numChars: Int): Word? {
        // this is essentially checkLength(numChars)
        val forLength = corpus.longsForLength(numChars) ?: return null
        val encoded = RandEncoded.random(numChars)
        val match = forLength[encoded]
        return if (match.isNullOrEmpty()) {
            null
        } else {
            val string = StringEncoder.decode(encoded)
            val index = corpus.setMatched(string, numChars)
            return Word(string, index)
        }
    }
}
