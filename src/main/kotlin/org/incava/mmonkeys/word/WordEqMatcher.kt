package org.incava.mmonkeys.word

import org.incava.mmonkeys.match.Matcher

class WordEqMatcher(override val monkey: WordMonkey, private val sought: Word) : Matcher(monkey) {
    override fun runIteration(): Any? {
        val word = monkey.nextWord()
        return if (word == sought) word else null
    }
}