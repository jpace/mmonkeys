package org.incava.mmonkeys.match

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.rand.CalculatedRandoms

class StringLengthMatcher(monkey: Monkey, sought: String) : StringMatcher(monkey, sought) {
    val rand = CalculatedRandoms.getCalculated(monkey.typewriter.numChars())
    private val soughtLen = sought.length + 1

    override fun runIteration(): MatchData {
        // number of keystrokes at which we'll hit the end-of-word character
        // thus length == 1 means we'll hit at the first invocation, with
        // an empty string
        val length = rand.nextRand()
        if (length == soughtLen) {
            val word = monkey.nextChars(length - 1)
            if (word == sought) {
                return MatchData(true, length, sought)
            }
        }
        return MatchData(false, length, null)
    }
}
