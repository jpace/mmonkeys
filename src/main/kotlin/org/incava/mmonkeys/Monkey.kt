package org.incava.mmonkeys

import org.incava.mmonkeys.type.Typewriter

open class Monkey(val id: Int, val typewriter: Typewriter) {
    fun nextChar(): Char {
        return typewriter.nextCharacter()
    }

    fun nextString(): String {
        val builder = StringBuilder()
        while (true) {
            val ch = nextChar()
            if (ch == ' ') {
                return builder.toString()
            } else {
                builder.append(ch)
            }
        }
    }

    fun nextChars(length: Int): String {
        // returns a string of the given length
        val builder = StringBuilder()
        while (builder.length < length) {
            val ch = nextChar()
            if (ch != ' ') {
                builder.append(ch)
            }
        }
        return builder.toString()
    }

    override fun toString(): String {
        return "Monkey(id=$id, typewriter=$typewriter)"
    }
}
