package org.incava.mmonkeys.mky.corpus.dc

import org.incava.mmonkeys.mky.number.RandEncoded
import org.incava.mmonkeys.words.Word

class WordGenerator(corpus: DualCorpus, filterSupplier: (Int) -> LengthFilter) {
    private val encodedGenerator = EncodedGenerator(corpus)
    private val filteringGenerator = FilteringGenerator(corpus, filterSupplier)

    fun findMatch(numChars: Int): Word? {
        return if (numChars <= RandEncoded.Constants.MAX_ENCODED_CHARS) {
            // use long/encoded, convert back to string
            encodedGenerator.getWord(numChars)
        } else {
            // use "legacy"
            filteringGenerator.getWord(numChars)
        }
    }
}
