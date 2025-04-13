package org.incava.mmonkeys.mky.corpus.sc

import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.WordChecker
import org.incava.mmonkeys.mky.mind.TypeStrategy
import org.incava.mmonkeys.words.Words

open class DefaultMonkey(id: Int, private val checker: WordChecker, private val strategy: TypeStrategy) : Monkey(id) {
    private fun typeWord(): String {
        return strategy.typeWord()
    }

    override fun findMatches(): Words {
        val word = typeWord()
        val words = checker.processWord(word)
        recordWords(words)
        return words
    }
}