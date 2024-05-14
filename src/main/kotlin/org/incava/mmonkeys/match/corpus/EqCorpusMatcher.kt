package org.incava.mmonkeys.match.corpus

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.MatchData
import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.match.corpus.CorpusMatcher

class EqCorpusMatcher(monkey: Monkey, sought: Corpus) : CorpusMatcher(monkey, sought) {
    override fun check(): MatchData {
        val word = monkey.nextString()
        val match = sought.hasWord(word)
        return if (match.first) {
            sought.removeAt(match.second)
            match(word.length, match.second)
        } else
            noMatch(word.length)
    }
}