package org.incava.mmonkeys.mky

import org.incava.mmonkeys.mky.mgr.Manager
import org.incava.mmonkeys.type.Typewriter
import org.incava.mmonkeys.words.Attempt
import org.incava.mmonkeys.words.Attempts
import org.incava.mmonkeys.words.Words

abstract class Monkey(val id: Int, val typewriter: Typewriter, var manager: Manager? = null) {
    override fun toString(): String = "Monkey(id=$id)"

    abstract fun findMatches(): Words

    fun processAttempts(attempts: Attempts) : Words {
        val words = attempts.asWords()
        manager?.update(this, words)
        return words
    }

    fun processAttempt(attempt: Attempt) : Words {
        val words = attempt.toWords()
        manager?.update(this, words)
        return words
    }
}