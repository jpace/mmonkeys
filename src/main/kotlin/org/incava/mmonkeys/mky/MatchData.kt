package org.incava.mmonkeys.mky

import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.words.Word
import org.incava.mmonkeys.words.Words

class MatchData(private val keystrokes: Int, val index: Int?) {
    fun toWords(corpus: Corpus): Words {
        // keystrokes here are only through the word, not the trailing space
        val totalKeystrokes = keystrokes.toLong() + 1
        return if (index == null)
            Words(totalKeystrokes, 1)
        else
            Words(listOf(Word(corpus.words[index], index)), totalKeystrokes, 1)
    }
}