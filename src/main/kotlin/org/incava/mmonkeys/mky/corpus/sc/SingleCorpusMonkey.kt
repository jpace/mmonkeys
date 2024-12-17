package org.incava.mmonkeys.mky.corpus.sc

import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.rand.RandomFactory
import org.incava.mmonkeys.type.Typewriter
import org.incava.mmonkeys.words.Word
import org.incava.mmonkeys.words.Words
import org.incava.rando.RandInt

abstract class SingleCorpusMonkey(id: Int, val typewriter: Typewriter, corpus: Corpus) : Monkey(id, corpus) {
    val rand: RandInt = RandomFactory.getCalculated(typewriter.numChars())

    // number of keystrokes at which we'll hit the end-of-word character
    // thus length == 1 means we'll hit at the first invocation, with
    // an empty string, 8 means we had 7 (hypothetical) characters,
    // and so on and so forth.
    fun randomLength() = rand.nextInt()

    fun toWordsMatch(word: String, index: Int, attemptKeyStrokes: Int): Words {
        val numAttempts = 1
        corpus.setMatched(index)
        return Words(listOf(Word(word, index)), attemptKeyStrokes.toLong(), numAttempts)
    }

    fun toNonMatch(attemptKeyStrokes: Int): Words {
        val numAttempts = 1
        return Words(attemptKeyStrokes.toLong(), numAttempts)
    }
}