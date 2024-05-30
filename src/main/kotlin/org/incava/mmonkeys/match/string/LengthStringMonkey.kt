package org.incava.mmonkeys.match.string

import org.incava.mmonkeys.match.MatchData
import org.incava.mmonkeys.type.Typewriter

class LengthStringMonkey(sought: String, id: Int, typewriter: Typewriter) : StringMonkey(sought, id, typewriter) {
    private val soughtLen = sought.length + 1

    override fun check(): MatchData {
        val length = randomLength()
        if (length == soughtLen) {
            val word = nextChars(length - 1)
            if (word == sought) {
                return match(length)
            }
        }
        return noMatch(length)
    }
}
