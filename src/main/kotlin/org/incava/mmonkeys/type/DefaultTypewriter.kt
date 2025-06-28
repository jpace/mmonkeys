package org.incava.mmonkeys.type

import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.MonkeyMonitor
import org.incava.mmonkeys.mky.WordChecker

open class DefaultTypewriter(private val checker: WordChecker, val manager: MonkeyMonitor) : Typewriter() {
    fun type(monkey: Monkey, string: String) {
        val attempt = checker.processAttempt(string)
        manager.update(monkey, attempt)
    }
}