package org.incava.mmonkeys.mky.string

import org.incava.mmonkeys.mky.MatchData
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter

class PartialStringMonkey(sought: String, id: Int, typewriter: Typewriter) : StringMonkey(sought, id, typewriter) {
    override fun check(): MatchData {
        var idx = 0
        val len = sought.length
        while (idx < len) {
            val ch = nextChar()
            when (ch) {
                Keys.END_CHAR -> {
                    return noMatch(idx)
                }

                sought[idx] -> {
                    ++idx
                    if (idx == len) {
                        return if (nextChar() == Keys.END_CHAR)
                            match(len)
                        else
                            noMatch()
                    }
                }

                else -> return noMatch()
            }
        }
        return noMatch()
    }

    private fun noMatch(): MatchData {
        // randomLength is always 1+, not zero
        val estimated = randomLength()
        return super.noMatch(estimated - 1)
    }
}