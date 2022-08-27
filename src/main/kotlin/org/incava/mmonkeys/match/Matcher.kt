package org.incava.mmonkeys.match

import org.incava.mmonkeys.Monkey

abstract class Matcher(val monkey: Monkey)  {
    abstract fun check(): MatchData

    fun match(keystrokes: Int, index: Int) : MatchData {
        return MatchData(true, keystrokes, index)
    }

    fun noMatch(keystrokes: Int) : MatchData {
        return MatchData(false, keystrokes, -1)
    }
}