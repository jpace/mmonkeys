package org.incava.mmonkeys.mky.corpus.dc

import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.type.Typewriter
import org.incava.mmonkeys.words.Words

class WordsGeneratorMonkey(id: Int, private val generator: WordsGenerator, typewriter: Typewriter) : Monkey(id, typewriter) {
    override fun findMatches(): Words {
        val words = generator.attemptMatch()
        recordWords(words)
        return words
    }
}