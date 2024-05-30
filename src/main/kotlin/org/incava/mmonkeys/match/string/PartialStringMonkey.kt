package org.incava.mmonkeys.match.string

import org.incava.mmonkeys.match.MatchData
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter

class PartialStringMonkey(sought: String, id: Int, typewriter: Typewriter) : StringMonkey(sought, id, typewriter) {
    override fun check(): MatchData {
        var idx = 0
        val len = sought.length
        while (idx < len) {
            val ch = nextChar()
            if (ch == Keys.END_CHAR) {
                return noMatch(idx)
            } else if (ch == sought[idx]) {
                ++idx
                if (idx == len) {
                    return if (nextChar() == Keys.END_CHAR)
                        match(len)
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

    private fun noMatch(): MatchData {
        val estimated = randomLength()
        return MatchData(false, estimated, -1)
    }
}