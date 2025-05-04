package org.incava.mmonkeys.mky

import org.incava.mmonkeys.mky.mgr.Manager
import org.incava.mmonkeys.type.Typewriter
import org.incava.mmonkeys.words.Attempt
import org.incava.mmonkeys.words.Attempts
import org.incava.mmonkeys.words.Words

abstract class Monkey(val id: Int, open val typewriter: Typewriter, var manager: Manager? = null) {
    abstract fun findMatches(): Words

    fun processAttempts(attempts: Attempts): Words {
        manager?.update(this, attempts)
        return Words(attempts.words)
    }

    fun processAttempt(attempt: Attempt): Words {
        manager?.update(this, attempt)
        return Words(attempt.words)
    }
}