package org.incava.mmonkeys.words

object WordsFactory {
    fun toWordsMatch(word: String, index: Int): Words {
        // count the space in the attempt:
        return Words(listOf(Word(word, index)), word.length.toLong() + 1, numAttempts = 1)
    }

    fun toWordsNonMatch(word: String) : Words {
        // count the space in the attempt:
        return toWordsNonMatch(word.length.toLong() + 1, numAttempts = 1)
    }

    fun toWordsNonMatch(totalKeystrokes: Long, numAttempts: Int) : Words {
        return Words(totalKeystrokes, numAttempts)
    }
}