package org.incava.mmonkeys.match.string

import org.incava.mmonkeys.match.MatchData
import org.incava.mmonkeys.type.Typewriter

class EqStringMonkey(sought: String, id: Int, typewriter: Typewriter) : StringMonkey(sought, id, typewriter) {
    override fun check(): MatchData {
        val word = nextString()
        return if (word == sought)
            match(word.length)
        else
            noMatch(word.length)
    }
}