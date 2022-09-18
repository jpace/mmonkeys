package org.incava.mmonkeys.match

import org.incava.mmonkeys.Monkey

class EqStringMatcher(monkey: Monkey, sought: String) : StringMatcher(monkey, sought) {
    override fun check(): MatchData {
        val word = monkey.nextString()
        return if (word == sought)
            match(word.length, 0)
        else
            noMatch(word.length)
    }
}