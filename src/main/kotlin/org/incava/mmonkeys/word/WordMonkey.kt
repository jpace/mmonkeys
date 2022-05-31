package org.incava.mmonkeys.word

import org.incava.mmonkeys.Typewriter

class WordMonkey(internal val id: Int, private val typewriter: Typewriter) {
    private fun nextChar(): Char {
        return typewriter.nextCharacter()
    }

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
