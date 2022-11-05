package org.incava.mmonkeys

import org.incava.mmonkeys.match.number.Maths
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter
import kotlin.random.Random

open class Monkey(val id: Int, val typewriter: Typewriter) {
    private var numValidChars = typewriter.numChars() - 1

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

    fun nextInt(digits: Int): Int {
        val max = Maths.power2(numValidChars, digits) * 2
        return Random.nextInt(max)
    }

    fun nextLong(digits: Int): Long {
        val max = Maths.power2(numValidChars.toLong(), digits) * 2
        return Random.nextLong(max)
    }

    override fun toString(): String {
        return "Monkey(id=$id, typewriter=$typewriter)"
    }
}
