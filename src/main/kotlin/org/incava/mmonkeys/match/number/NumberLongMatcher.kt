package org.incava.mmonkeys.match.number

import org.incava.ikdk.math.Maths
import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.MatchData
import org.incava.mmonkeys.match.string.StringMatcher
import kotlin.random.Random

class NumberLongMatcher(monkey: Monkey, sought: String) : StringMatcher(monkey, sought) {
    private val number = StringEncoder.encodeToLong(sought)
    private val soughtLen = sought.length
    private val max = Maths.powerLongRepeat(monkey.typewriter.numChars().toLong(), soughtLen) * 2

    override fun check(): MatchData {
        // number of keystrokes at which we'll hit the end-of-word character
        // thus length == 1 means we'll hit at the first invocation, with
        // an empty string
        val length = rand.nextRand()
        if (length == soughtLen + 1) {
            val num = Random.nextLong(max)
            if (num == number) {
                return match(length, 0)
            }
        }
        return noMatch(length)
    }
}