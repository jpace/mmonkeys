package org.incava.mmonkeys.mky.corpus.dc

import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.words.Attempts
import org.incava.mmonkeys.words.Words

class WordsGeneratorMonkey(id: Int, private val generator: WordsGenerator, private val typewriter: AttemptedTypewriter): Monkey(id) {
    override fun findMatches(): Words {
        val attempts = runAttempts()
        return Words(attempts.words)
    }

    fun runAttempts() : Attempts {
        val attempts = generator.runAttempts()
        typewriter.addAttempts(this, attempts)
        return attempts
    }
}