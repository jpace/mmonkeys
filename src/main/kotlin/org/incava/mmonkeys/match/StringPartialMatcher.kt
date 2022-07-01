package org.incava.mmonkeys.match

import org.incava.mmonkeys.Monkey

open class StringPartialMatcher(monkey: Monkey, private val sought: String) : Matcher(monkey) {
    override fun runIteration(): Boolean {
        var idx = 0
        val len = sought.length
        while (idx < len) {
            val ch = monkey.nextChar()
            if (ch == sought[idx]) {
                ++idx
                if (idx == len) {
                    return true
                }
            } else {
                break
            }
        }
        return false
    }
}