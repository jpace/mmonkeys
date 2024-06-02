package org.incava.mmonkeys.match

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.type.Typewriter

class MatcherMonkey(val matcher: Matching, id: Int, typewriter: Typewriter) : Monkey(id, typewriter) {
    override fun check(): MatchData {
        return matcher.check()
    }

    override fun match(keystrokes: Int, index: Int): MatchData {
        return matcher.match(keystrokes, index)
    }
}