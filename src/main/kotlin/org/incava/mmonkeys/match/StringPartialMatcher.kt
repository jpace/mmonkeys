package org.incava.mmonkeys.match

import org.incava.mmonkeys.Monkey

class StringPartialMatcher(monkey: Monkey, sought: String) : StringMatcher(monkey, sought) {
    override fun runIteration(): String? {
        var idx = 0
        val len = sought.length
        while (idx < len) {
            val ch = monkey.nextChar()
            if (ch == sought[idx]) {
                ++idx
                if (idx == len) {
                    return if (monkey.nextChar() == ' ') sought else null
                }
            } else {
                break
            }
        }
        return null
    }
}