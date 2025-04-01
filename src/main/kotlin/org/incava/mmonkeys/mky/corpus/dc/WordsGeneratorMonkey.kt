package org.incava.mmonkeys.mky.corpus.dc

import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.words.Words

class WordsGeneratorMonkey(id: Int, private val generator: WordsGenerator) : Monkey(id) {
    override fun findMatches(): Words {
        val words = generator.attemptMatch()
        recordWords(words)
        return words
    }
}