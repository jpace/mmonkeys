package org.incava.mmonkeys.match

import org.incava.mmonkeys.word.Word
import org.incava.mmonkeys.word.WordMonkey

class WordEqMatcher(override val monkey: WordMonkey, private val sought: Word) : Matcher(monkey) {
    override fun runIteration(): Boolean {
        val word = monkey.nextWord()
        return word == sought
    }
}