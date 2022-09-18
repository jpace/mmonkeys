package org.incava.mmonkeys.match

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.rand.CalculatedRandoms

class LengthStringMatcher(monkey: Monkey, sought: String) : StringMatcher(monkey, sought) {
    val rand = CalculatedRandoms.getCalculated(monkey.typewriter.numChars())
    private val soughtLen = sought.length + 1

    override fun check(): MatchData {
        // number of keystrokes at which we'll hit the end-of-word character
        // thus length == 1 means we'll hit at the first invocation, with
        // an empty string
        val length = rand.nextRand()
        if (length == soughtLen) {
            val word = monkey.nextChars(length - 1)
            if (word == sought) {
                return match(length, 0)
            }
        }
        return noMatch(length)
    }
}
