package org.incava.mmonkeys.mky.string

import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.MatchData
import org.incava.mmonkeys.type.Typewriter

abstract class StringMonkey(val sought: String, id: Int, typewriter: Typewriter) : Monkey(id, typewriter) {
    var complete = false

    override fun match(keystrokes: Int, index: Int): MatchData {
        complete = true
        return super.match(keystrokes, index)
    }

    // string matches are always index 0, since there's no corpus
    fun match(keystrokes: Int) = match(keystrokes, 0)
}