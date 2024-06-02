package org.incava.mmonkeys.trials.string

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.MatchData
import org.incava.mmonkeys.match.string.StringMonkey
import org.incava.mmonkeys.type.Typewriter

class NoOpMonkey(monkey: Monkey, sought: String, id: Int, typewriter: Typewriter) : StringMonkey(sought, id, typewriter) {
    override fun check(): MatchData {
        return match(0)
    }
}