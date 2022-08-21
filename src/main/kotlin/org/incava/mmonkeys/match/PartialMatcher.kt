package org.incava.mmonkeys.match

import org.incava.mmonkeys.Monkey

class PartialMatcher(monkey: Monkey, sought: String) : Matcher(monkey, sought) {
    override fun runIteration(): MatchData {
        var idx = 0
        val len = sought.length
        while (idx < len) {
            val ch = monkey.nextChar()
            if (ch == sought[idx]) {
                ++idx
                if (idx == len) {
                    return if (monkey.nextChar() == ' ') match(len, 0) else noMatch(len)
                }
            } else {
                break
            }
        }
        return noMatch(idx)
    }
}