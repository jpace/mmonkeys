package org.incava.mmonkeys.type

import org.incava.mmonkeys.mky.WordChecker
import org.incava.mmonkeys.words.Attempt

open class DefaultTypewriter(private val checker: WordChecker) : Typewriter() {
    fun toAttempt(string: String) : Attempt {
        // Qlog.info("string", string)
        return checker.processAttempt(string)
    }
}