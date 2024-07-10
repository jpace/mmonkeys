package org.incava.mmonkeys.trials.string

import org.incava.mmonkeys.mky.MatchData
import org.incava.mmonkeys.mky.string.StringMonkey
import org.incava.mmonkeys.type.Typewriter

class NoOpMonkey(sought: String, id: Int, typewriter: Typewriter) : StringMonkey(sought, id, typewriter) {
    override fun check(): MatchData {
        return match(0)
    }
}