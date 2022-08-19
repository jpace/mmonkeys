package org.incava.mmonkeys.match

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.rand.CalculatedRandoms

class StringLengthMatcher(monkey: Monkey, sought: String) : StringMatcher(monkey, sought) {
    val rand = CalculatedRandoms.getCalculated(monkey.typewriter.numChars())
    private val soughtLen = sought.length + 1

    override fun runIteration(): Any? {
        // number of keystrokes at which we'll hit the end-of-word character
        // thus length == 1 means we'll hit at the first invocation, with
        // an empty string
        // TODO: fix this casting to store the slots as longs, not doubles.
        val length = rand.nextRand().toInt()
        return if (length == soughtLen) {
            val word = monkey.nextChars(length - 1)
            word == sought
        } else {
            null
        }
    }
}