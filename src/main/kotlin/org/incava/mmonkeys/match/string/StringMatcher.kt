package org.incava.mmonkeys.match.string

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.MatchData
import org.incava.mmonkeys.match.Matcher

abstract class StringMatcher(monkey: Monkey, val sought: String) : Matcher(monkey) {

    // string matches are always index 0
    fun match(keystrokes: Int) = match(keystrokes, 0)
}