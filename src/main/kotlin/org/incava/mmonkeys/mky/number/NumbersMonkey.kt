package org.incava.mmonkeys.mky.number

import org.incava.mmonkeys.mky.corpus.sc.SingleCorpusMonkey
import org.incava.mmonkeys.rand.RandomFactory
import org.incava.mmonkeys.type.Typewriter
import org.incava.mmonkeys.words.Words
import org.incava.rando.RandInt

class NumbersMonkey(id: Int, typewriter: Typewriter, override val corpus: NumberedCorpus) : SingleCorpusMonkey(id, typewriter, corpus) {
    val rand: RandInt = RandomFactory.getCalculated(typewriter.chars.size)

    override fun findMatches(): Words {
        // number of keystrokes at which we'll hit the end-of-word character
        // thus length == 1 means we'll hit at the first invocation, with
        // an empty string, 8 means we had 7 (hypothetical) characters,
        // and so on and so forth.
        val length = rand.nextInt()
        val numChars = length - 1
        val forLength = corpus.longsForLength(numChars)
        // if null, we must be called with the wrong (> 13) length:
        if (forLength != null) {
            val encoded = RandEncoded.random(numChars)
            val forEncoded = forLength[encoded]
            if (!forEncoded.isNullOrEmpty()) {
                val index = forEncoded.removeAt(0)
                val word = corpus.words[index]
                return toWordsMatch(word, index)
            }
        }
        val numAttempts = 1
        return Words(length.toLong(), numAttempts)
    }
}