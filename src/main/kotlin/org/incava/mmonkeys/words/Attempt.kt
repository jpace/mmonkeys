package org.incava.mmonkeys.words

/**
 * Attempts are indexed at the number of keystrokes of the attempt,
 * at which the word was generated. So zero means the word was
 * generated at the first attempt.
 */
data class Attempt(val matches: Map<Long, Word>, val totalKeyStrokes: Long, val numAttempts: Int) {
    val words get() = matches.values

    fun asWords() : Words {
        return Words(words.toList(), totalKeyStrokes, numAttempts)
    }
}