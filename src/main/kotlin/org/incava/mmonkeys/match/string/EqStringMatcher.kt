package org.incava.mmonkeys.match.string

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.MatchData

class EqStringMatcher(monkey: Monkey, sought: String) : StringMatcher(monkey, sought) {
    override fun check(): MatchData {
        val word = monkey.nextString()
        return if (word == sought)
            match(word.length)
        else
            noMatch(word.length)
    }
}