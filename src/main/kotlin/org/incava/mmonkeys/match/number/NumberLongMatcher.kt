package org.incava.mmonkeys.match.number

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.MatchData
import org.incava.mmonkeys.match.Matcher
import org.incava.mmonkeys.rand.CalculatedRandoms
import org.incava.mmonkeys.util.Console

class NumberLongMatcher(monkey: Monkey, val sought: String) : Matcher(monkey) {
    val number = StringEncoder.encodeToLong(sought)
    private val soughtLen = sought.length

    override fun check(): MatchData {
        // number of keystrokes at which we'll hit the end-of-word character
        // thus length == 1 means we'll hit at the first invocation, with
        // an empty string
        tick()

        val length = rand.nextRand()
        if (length == soughtLen + 1) {
            val num = monkey.nextLong(soughtLen)
            if (num == number) {
                return match(length, 0)
            }
        }
        return noMatch(length)
    }
}