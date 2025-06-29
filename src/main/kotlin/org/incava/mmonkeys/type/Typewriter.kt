package org.incava.mmonkeys.type

import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.WordChecker

class Typewriter(private val checker: WordChecker) {
    fun type(monkey: Monkey, string: String) {
        checker.check(monkey, string)
    }
}