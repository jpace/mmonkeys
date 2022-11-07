package org.incava.mmonkeys.match.string

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.MatchData
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.util.Console
import java.util.concurrent.atomic.AtomicLong

class PartialStringMatcher(monkey: Monkey, sought: String) : StringMatcher(monkey, sought) {
    override fun check(): MatchData {
        tick()
        var idx = 0
        val len = sought.length
        while (idx < len) {
            val ch = monkey.nextChar()
            if (ch == sought[idx]) {
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
}