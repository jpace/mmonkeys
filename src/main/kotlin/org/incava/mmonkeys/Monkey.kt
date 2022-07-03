package org.incava.mmonkeys

import org.incava.mmonkeys.type.Typewriter

open class Monkey(val id: Int, private val typewriter: Typewriter) {
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

    override fun toString(): String {
        return "Monkey(id=$id, typewriter=$typewriter)"
    }
}
