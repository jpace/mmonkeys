package org.incava.mmonkeys.match

import org.incava.mmonkeys.Monkey

class EqMatcher(monkey: Monkey, sought: String) : Matcher(monkey, sought) {
    override fun runIteration(): MatchData {
        val word = monkey.nextString()
        return if (word == sought)
            match(word.length, 0)
        else
            noMatch(word.length)
    }
}