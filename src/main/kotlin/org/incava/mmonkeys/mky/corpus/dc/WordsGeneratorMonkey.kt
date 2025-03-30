package org.incava.mmonkeys.mky.corpus.dc

import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.words.Attempt
import org.incava.mmonkeys.words.Words

class WordsGeneratorMonkey(id: Int, private val generator: WordsGenerator) : Monkey(id) {
    override fun findMatches(): Words {
        val attempt = generator.attemptMatch()
        val words = toWords(attempt)
        recordWords(words)
        return words
    }

    fun toWords(attempt: Attempt): Words {
        return attempt.asWords()
    }
}