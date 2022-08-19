package org.incava.mmonkeys.match

import org.incava.mmonkeys.Monkey

class StringEqMatcher(monkey: Monkey, sought: String) : StringMatcher(monkey, sought) {
    override fun runIteration(): Any? {
        val word = monkey.nextString()
        return if (word == sought) sought else null
    }
}