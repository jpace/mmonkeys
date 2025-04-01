package org.incava.mmonkeys.mky.corpus.sc

import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.WordChecker
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.mind.TypeStrategy
import org.incava.mmonkeys.words.Words

open class CorpusMonkey(id: Int, val checker: WordChecker, private val strategy: TypeStrategy) : Monkey(id) {
    fun typeWord(): String {
        return strategy.typeWord()
    }

    override fun findMatches(): Words {
        val word = typeWord()
        val words = checker.processWord(word)
        recordWords(words)
        return words
    }
}