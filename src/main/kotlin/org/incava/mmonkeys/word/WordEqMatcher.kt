package org.incava.mmonkeys.word

import org.incava.mmonkeys.match.MatchData
import org.incava.mmonkeys.match.Matcher

class WordEqMatcher(override val monkey: WordMonkey, private val sought: Word) : Matcher(monkey) {
    override fun runIteration(): MatchData {
        val word = monkey.nextWord()
        return if (word == sought)
            MatchData(true, word.length(), word)
        else
            MatchData(false, word.length(), null)
    }
}