package org.incava.mmonkeys.match

import org.incava.mmonkeys.Monkey

class EqStringMatcher(monkey: Monkey, sought: Corpus) : StringMatcher(monkey, sought.words.first()) {
    override fun check(): MatchData {
        val word = monkey.nextString()
        return if (word == sought)
            match(word.length, 0)
        else
            noMatch(word.length)
    }
}