package org.incava.mmonkeys.match.corpus

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.MatchData

class EqCorpusMatcher(monkey: Monkey, sought: Corpus) : CorpusMatcher(monkey, sought) {
    override fun check(): MatchData {
        val word = monkey.nextString()
        val result = sought.hasWord(word)
        return if (result.first)
            match(word.length, 0)
        else
            noMatch(word.length)
    }
}