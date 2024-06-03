package org.incava.mmonkeys.match.corpus

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.MatchData
import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.match.corpus.CorpusMatcher

class EqCorpusMatcher(monkey: Monkey, sought: Corpus) : CorpusMatcher(monkey, sought) {
    override fun check(): MatchData {
        val word = nextString()
        val index = sought.match(word)
        return if (index >= 0) {
            sought.removeAt(index)
            match(word.length, index)
        } else
            noMatch(word.length)
    }
}