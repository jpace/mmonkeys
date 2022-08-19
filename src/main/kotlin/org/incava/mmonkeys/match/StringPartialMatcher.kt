package org.incava.mmonkeys.match

import org.incava.mmonkeys.Monkey

class StringPartialMatcher(monkey: Monkey, sought: String) : StringMatcher(monkey, sought) {
    override fun runIteration(): MatchData {
        var idx = 0
        val len = sought.length
        while (idx < len) {
            val ch = monkey.nextChar()
            if (ch == sought[idx]) {
                ++idx
                if (idx == len) {
                    return if (monkey.nextChar() == ' ') MatchData(true, len, sought) else MatchData(false, len, null)
                }
            } else {
                break
            }
        }
        return MatchData(false, idx, null)
    }
}