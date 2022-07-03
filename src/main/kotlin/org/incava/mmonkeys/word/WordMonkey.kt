package org.incava.mmonkeys.word

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.type.Typewriter

class WordMonkey(id: Int, private val typewriter: Typewriter) : Monkey(id, typewriter) {
    fun nextWord(): Word {
        val word = Word()
        while (true) {
            val ch = nextChar()
            if (ch == ' ') {
                return word
            } else {
                word += ch
            }
        }
    }

    override fun toString(): String {
        return "WordMonkey(id=$id, typewriter=$typewriter)"
    }
}
