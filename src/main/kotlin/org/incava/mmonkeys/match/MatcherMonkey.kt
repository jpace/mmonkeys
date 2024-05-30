package org.incava.mmonkeys.match

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.type.Typewriter

class MatcherMonkey(val matcher: Matcher, id: Int, typewriter: Typewriter) :
    Monkey(id, typewriter) {
    fun check(): MatchData {
        return matcher.check()
    }

    override fun match(keystrokes: Int, index: Int): MatchData {
        return matcher.match(keystrokes, index)
    }
}