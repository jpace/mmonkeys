package org.incava.mmonkeys.mky

import org.incava.mmonkeys.mky.mgr.Manager
import org.incava.mmonkeys.words.Attempt
import org.incava.mmonkeys.words.Attempts
import org.incava.mmonkeys.words.Words

abstract class Monkey(val id: Int, var manager: Manager? = null) {
    abstract fun findMatches(): Words

    fun processAttempts(attempts: Attempts) {
        manager?.update(this, attempts)
    }

    fun processAttempt(attempt: Attempt) {
        manager?.update(this, attempt)
    }
}