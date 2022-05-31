package org.incava.mmonkeys.match

import org.incava.mmonkeys.word.Word
import org.incava.mmonkeys.word.WordMonkey

class WordEqMatcher(private val monkey: WordMonkey) {
    fun run(sought: Word, maxAttempts: Long = 1_000_000_000_000L): Long? {
        for (iteration in 0 until maxAttempts) {
            val word = monkey.nextWord()
            if (word == sought) {
                return iteration
            }
        }
        return null
    }
}