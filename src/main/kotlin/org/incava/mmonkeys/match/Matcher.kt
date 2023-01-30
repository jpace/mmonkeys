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

    abstract fun isComplete(): Boolean
}