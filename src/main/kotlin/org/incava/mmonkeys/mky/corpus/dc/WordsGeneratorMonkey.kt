package org.incava.mmonkeys.mky.corpus.dc

import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.mgr.Manager
import org.incava.mmonkeys.type.Typewriter
import org.incava.mmonkeys.words.Attempts
import org.incava.mmonkeys.words.Words

class WordsGeneratorMonkey(id: Int, private val generator: WordsGenerator, typewriter: Typewriter): Monkey(id, typewriter) {
    override fun findMatches(): Words {
        val attempts = generator.runAttempts()
        return processAttempts(attempts)
    }

    fun runAttempts() : Attempts {
        val attempts = generator.runAttempts()
        processAttempts(attempts)
        return attempts
    }
}