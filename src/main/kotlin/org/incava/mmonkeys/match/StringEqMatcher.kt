package org.incava.mmonkeys.match

import org.incava.mmonkeys.Monkey

class StringEqMatcher(private val monkey: Monkey) {
    fun run(sought: String, maxAttempts: Long = 1_000_000_000_000L): Long? {
        for (iteration in 0 until maxAttempts) {
            val word = monkey.nextString()
            if (word == sought) {
                return iteration
            }
        }
        return null
    }
}