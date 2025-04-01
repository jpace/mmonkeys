package org.incava.mmonkeys.words

/**
 * Attempts are indexed at the number of keystrokes of the attempt,
 * at which the word was generated. So zero means the word was
 * generated at the first attempt.
 */
data class Attempt(val words: List<Word>, val totalKeyStrokes: Long, val numAttempts: Int) {
    fun asWords(): Words {
        return Words(words, totalKeyStrokes, numAttempts)
    }
}