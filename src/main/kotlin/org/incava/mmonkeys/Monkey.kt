package org.incava.mmonkeys

import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter

open class Monkey(val id: Int, val typewriter: Typewriter) {
    fun nextChar(): Char {
        return typewriter.nextCharacter()
    }

    fun nextString(): String {
        val builder = StringBuilder()
        while (true) {
            val ch = nextChar()
            if (ch == Keys.END_CHAR) {
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
            if (ch != Keys.END_CHAR) {
                builder.append(ch)
            }
        }
        return builder.toString()
    }

    fun nextWordChars(length: Int): String {
        // returns a string of the given length
        return (0 until length).fold(StringBuilder()) { sb, _ ->
            val ch = typewriter.nextWordCharacter()
            sb.append(ch)
        }.toString()
    }

    override fun toString(): String {
        return "Monkey(id=$id, typewriter=$typewriter)"
    }
}
