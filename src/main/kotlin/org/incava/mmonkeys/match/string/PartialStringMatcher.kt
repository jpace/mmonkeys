package org.incava.mmonkeys.match.string

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.MatchData
import org.incava.mmonkeys.type.Keys

class PartialStringMatcher(monkey: Monkey, sought: String) : StringMatcher(monkey, sought) {
    override fun check(): MatchData {
        var idx = 0
        val len = sought.length
        while (idx < len) {
            val ch = monkey.nextChar()
            if (ch == Keys.END_CHAR) {
                return noMatch(idx)
            } else if (ch == sought[idx]) {
                ++idx
                if (idx == len) {
                    return if (monkey.nextChar() == Keys.END_CHAR)
                        match(len, 0)
                    else {
                        noMatch()
                    }
                }
            } else {
                return noMatch()
            }
        }
        return noMatch()
    }

    fun noMatch(): MatchData {
        val estimated = rand.nextRand()
        return MatchData(false, estimated, -1)
    }
}