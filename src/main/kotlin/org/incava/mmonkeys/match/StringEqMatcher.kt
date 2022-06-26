package org.incava.mmonkeys.match

import org.incava.mmonkeys.Monkey

open class StringEqMatcher(monkey: Monkey, private val sought: String) : Matcher(monkey) {
    override fun runIteration(): Boolean {
        val word = monkey.nextString()
        return word == sought
    }
}