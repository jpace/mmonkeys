package org.incava.mmonkeys.mky.corpus.sc

import org.incava.mmonkeys.corpus.Corpus
import org.incava.mmonkeys.words.Word
import org.incava.mmonkeys.words.Words
import org.incava.mmonkeys.words.WordsFactory

object MatchResults {
    fun toWords(words: List<Word>, keystrokes: Long, numAttempts: Int): Words {
        return Words(words, keystrokes, numAttempts)
    }

    fun toWordsMatch(corpus: Corpus, word: String, index: Int): Words {
        corpus.setMatched(index)
        return WordsFactory.toWordsMatch(word, index)
    }

    fun toNonMatch(word: String): Words {
        return WordsFactory.toWordsNonMatch(word)
    }
}