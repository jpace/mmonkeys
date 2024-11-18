package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.mky.corpus.DualCorpus
import org.incava.mmonkeys.mky.number.RandEncoded
import org.incava.mmonkeys.mky.number.StringEncoder

class EncodedGenerator(val corpus: DualCorpus) {
    fun getWord(numChars: Int): String? {
        // this is essentially checkLength(numChars)
        val forLength = corpus.longsForLength(numChars) ?: return null
        val encoded = RandEncoded.random(numChars)
        val match = forLength[encoded]
        // @todo - remove from corpus.numbers
        return if (match.isNullOrEmpty()) {
            null
        } else {
            StringEncoder.decode(encoded).also {
                corpus.matched(encoded, numChars)
            }
        }
    }
}
