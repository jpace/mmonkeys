package org.incava.mmonkeys.mky.corpus.sc

import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.words.Word
import org.incava.mmonkeys.words.Words

object MatchResults {
    fun toWords(words: List<Word>, keystrokes: Long, numAttempts: Int): Words {
        return Words(words, keystrokes, numAttempts)
    }

    fun toWordsMatch(corpus: Corpus, word: String, index: Int): Words {
        val numAttempts = 1
        corpus.setMatched(index)
        // count the space in the attempt:
        return Words(listOf(Word(word, index)), word.length.toLong() + 1, numAttempts)
    }

    fun toNonMatch(word: String): Words {
        // count the space in the attempt:
        val numAttempts = 1
        return Words(word.length.toLong() + 1, numAttempts)
    }
}