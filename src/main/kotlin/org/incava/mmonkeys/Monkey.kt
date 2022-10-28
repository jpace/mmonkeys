package org.incava.mmonkeys

import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter
import org.incava.mmonkeys.util.Console
import kotlin.math.pow
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

    fun nextInt(digits: Int) : Int {
        val max = numValidChars.toDouble().pow(digits).toInt() * 2
        // Console.info("max", max)
        val value = Random.nextInt(max)
        // Console.info("value", value)
        return value
    }

    override fun toString(): String {
        return "Monkey(id=$id, typewriter=$typewriter)"
    }
}
