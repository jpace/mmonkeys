package org.incava.mmonkeys.trials.rand

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.number.NumberedCorpus
import org.incava.mmonkeys.mky.number.StringEncoder
import kotlin.random.Random

class EncodedGenerator(val corpus: NumberedCorpus) {
    fun getWord(numChars: Int): String? {
        val rangeEncoded = corpus.rangeEncoded[numChars] ?: return null
        // range = (x + 1) * 26 - x
        //  step 1: 26x + 26 - x
        //  step 2: 25x + 26
        //  step 3: profit!
        val range = rangeEncoded.first * 25 + 26
        val randInRange = Random.nextLong(range)
        val encoded = rangeEncoded.first + randInRange
        val forLength = corpus.numbers[numChars] ?: return null
        val match = forLength[encoded]
        // @todo - remove from corpus.numbers
        return if (match == null) {
            null
        } else {
            if (match.isEmpty()) {
                Console.info("match.empty", match)
                null
            } else {
                val word = StringEncoder.decode(encoded)
                corpus.matched(word, encoded, numChars)
                word
            }
        }
    }
}
