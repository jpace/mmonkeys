package org.incava.mmonkeys.mky.corpus.dc

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.type.Typewriter
import org.incava.mmonkeys.words.Attempt
import org.incava.mmonkeys.words.Attempts

class AttemptedTypewriter : Typewriter() {
    fun addAttempt(attempt: Attempt) {
        // Qlog.info("attempt", attempt)
    }

    fun addAttempts(attempts: Attempts) {
        // Qlog.info("attempts", attempts)
    }
}