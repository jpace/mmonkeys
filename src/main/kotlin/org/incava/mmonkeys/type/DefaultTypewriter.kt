package org.incava.mmonkeys.type

import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.MonkeyMonitor
import org.incava.mmonkeys.mky.WordChecker
import org.incava.mmonkeys.words.Attempt

open class DefaultTypewriter(private val checker: WordChecker, val manager: MonkeyMonitor) : Typewriter() {
    fun addAttempt(monkey: Monkey, string: String): Attempt {
        val attempt = checker.processAttempt(string)
        manager.update(monkey, attempt)
        return attempt
    }
}