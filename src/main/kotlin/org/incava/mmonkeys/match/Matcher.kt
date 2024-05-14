package org.incava.mmonkeys.match

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.rand.RandomFactory

abstract class Matcher(val monkey: Monkey) {
    val rand = RandomFactory.getCalculated(monkey.typewriter.numChars())

    abstract fun check(): MatchData

    open fun match(keystrokes: Int, index: Int): MatchData {
        return MatchData(true, keystrokes, index)
    }

    fun noMatch(keystrokes: Int): MatchData {
        return MatchData(false, keystrokes, -1)
    }

    // number of keystrokes at which we'll hit the end-of-word character
    // thus length == 1 means we'll hit at the first invocation, with
    // an empty string, 8 means we had 7 (hypothetical) characters,
    // and so on and so forth.
    fun randomLength() = rand.nextRand()

    abstract fun isComplete(): Boolean
}