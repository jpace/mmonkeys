package org.incava.mmonkeys.match.string

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.MatchData
import org.incava.mmonkeys.match.Matcher

abstract class StringMatcher(monkey: Monkey, val sought: String) : Matcher(monkey) {
    private var complete = false

    override fun isComplete(): Boolean {
        return complete
    }

    override fun match(keystrokes: Int, index: Int): MatchData {
        complete = true
        return super.match(keystrokes, index)
    }
}