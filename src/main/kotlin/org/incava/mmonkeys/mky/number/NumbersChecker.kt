package org.incava.mmonkeys.mky.number

import org.incava.mmonkeys.words.Attempt
import org.incava.mmonkeys.words.AttemptFactory
import org.incava.mmonkeys.words.Word

class NumbersChecker(val corpus: NumberedCorpus) {
    fun hasForLength(numChars: Int) = corpus.hasForLength(numChars)

    fun toAttempt(numChars: Int, encoded: Long): Attempt {
        val forLength = corpus.longsForLength(numChars)!!
        val forEncoded = forLength[encoded]
        return if (forEncoded.isNullOrEmpty()) {
            // keystrokes is through the space
            AttemptFactory.failed(numChars + 1)
        } else {
            val str = StringEncoder.decode(encoded)
            val index = forEncoded[0]
            corpus.matches.setMatched(index)
            corpus.removeItem(encoded, numChars)
            val word = Word(str, index)
            return AttemptFactory.succeeded(word)
        }
    }
}