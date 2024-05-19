package org.incava.mmonkeys.match.string

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.MatchData

class LengthStringMatcher(monkey: Monkey, sought: String) : StringMatcher(monkey, sought) {
    private val soughtLen = sought.length + 1

    override fun check(): MatchData {
        val length = randomLength()
        if (length == soughtLen) {
            val word = monkey.nextChars(length - 1)
            if (word == sought) {
                return match(length)
            }
        }
        return noMatch(length)
    }
}
