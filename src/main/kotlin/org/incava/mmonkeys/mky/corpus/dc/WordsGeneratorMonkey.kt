package org.incava.mmonkeys.mky.corpus.dc

import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.type.Typewriter
import org.incava.mmonkeys.words.Attempts
import org.incava.mmonkeys.words.Words

class WordsGeneratorMonkey(id: Int, private val generator: WordsGenerator, typewriter: AttemptedTypewriter): Monkey(id, typewriter) {
    private val attemptedTypewriter = typewriter

    override fun findMatches(): Words {
        val attempts = runAttempts()
        return Words(attempts.words)
    }

    fun runAttempts() : Attempts {
        val attempts = generator.runAttempts()
        attemptedTypewriter.addAttempts(attempts)
        processAttempts(attempts)
        return attempts
    }
}