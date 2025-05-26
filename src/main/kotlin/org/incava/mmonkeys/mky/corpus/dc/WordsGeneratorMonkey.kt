package org.incava.mmonkeys.mky.corpus.dc

import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.MonkeyMonitor
import org.incava.mmonkeys.mky.mgr.Manager
import org.incava.mmonkeys.words.Attempts
import org.incava.mmonkeys.words.Words

class WordsGeneratorMonkey(id: Int, private val generator: WordsGenerator, private val typewriter: AttemptedTypewriter, manager: MonkeyMonitor): Monkey(id, manager) {
    override fun findMatches(): Words {
        val attempts = runAttempts()
        return Words(attempts.words)
    }

    fun runAttempts() : Attempts {
        val attempts = generator.runAttempts()
        typewriter.addAttempts(attempts)
        processAttempts(attempts)
        return attempts
    }
}