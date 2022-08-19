package org.incava.mmonkeys.match

import org.incava.mmonkeys.Monkey

class StringEqMatcher(monkey: Monkey, sought: String) : StringMatcher(monkey, sought) {
    override fun runIteration(): MatchData {
        val word = monkey.nextString()
        return if (word == sought) MatchData(true, word.length, word) else MatchData(false, word.length, null)
    }
}