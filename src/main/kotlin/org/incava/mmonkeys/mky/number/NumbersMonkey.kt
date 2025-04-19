package org.incava.mmonkeys.mky.number

import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.rand.RandomFactory
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter
import org.incava.mmonkeys.words.Words
import org.incava.mmonkeys.words.WordsFactory
import org.incava.rando.RandInt

class NumbersMonkey(id: Int, val corpus: NumberedCorpus, typewriter: Typewriter) : Monkey(id, typewriter) {
    val rand: RandInt = RandomFactory.getCalculated(Keys.fullList().size)

    override fun findMatches(): Words {
        // number of keystrokes at which we'll hit the end-of-word character
        // thus length == 1 means we'll hit at the first invocation, with
        // an empty string, 8 means we had 7 (hypothetical) characters,
        // and so on and so forth.
        val length = rand.nextInt()
        val numChars = length - 1
        val forLength = corpus.longsForLength(numChars)
        // if null, we must be called with the wrong (> 13) length:
        return if (forLength == null) {
            WordsFactory.toWordsNonMatch(length.toLong(), 1)
        } else {
            typeWord(numChars)
        }
    }

    fun typeWord(numChars: Int) : Words {
        val encoded = RandEncoded.random(numChars)
        val forLength = corpus.longsForLength(numChars)!!
        val forEncoded = forLength[encoded]
        if (forEncoded.isNullOrEmpty()) {
            // keystrokes is through the space
            return WordsFactory.toWordsNonMatch(numChars + 1L, 1)
        } else {
            val index = corpus.setMatched(encoded, numChars)
            val word = StringEncoder.decode(encoded)
            return toWordsMatch(word, index).also { recordWords(it) }
        }
    }

    private fun toWordsMatch(word: String, index: Int): Words {
        corpus.setMatched(index)
        return WordsFactory.toWordsMatch(word, index)
    }
}