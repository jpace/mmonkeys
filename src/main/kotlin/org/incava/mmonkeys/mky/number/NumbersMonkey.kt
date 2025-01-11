package org.incava.mmonkeys.mky.number

import org.incava.mmonkeys.mky.corpus.sc.SingleCorpusMonkey
import org.incava.mmonkeys.type.Typewriter
import org.incava.mmonkeys.words.Words

class NumbersMonkey(id: Int, typewriter: Typewriter, override val corpus: NumberedCorpus) : SingleCorpusMonkey(id, typewriter, corpus) {
    override fun findMatches(): Words {
        val length = randomLength()
        val numChars = length - 1
        val forLength = corpus.longsForLength(numChars) ?: return toNonMatch(length)

        // if null, we must be called with the wrong (> 13) length:
        val encoded = RandEncoded.random(numChars)
        val forEncoded = forLength[encoded]
        return if (forEncoded.isNullOrEmpty()) {
            toNonMatch(length)
        } else {
            val index = forEncoded.removeAt(0)
            val word = corpus.words[index]
            toWordsMatch(word, index, length)
        }
    }
}