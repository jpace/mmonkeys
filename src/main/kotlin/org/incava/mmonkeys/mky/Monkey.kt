package org.incava.mmonkeys.mky

import org.incava.mmonkeys.rand.RandomFactory
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter

abstract class Monkey(val id: Int, val typewriter: Typewriter) {
    val rand = RandomFactory.getCalculated(typewriter.numChars())
    val attempts = MonkeyAttemptsNoOp()
    var numAttempts = 0L
    var totalKeystrokes = 0L
    val matchKeystrokes = mutableMapOf<Int, Int>()

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
        return (0 until length).fold(StringBuilder()) { sb, _ ->
            val ch = typewriter.nextWordCharacter()
            sb.append(ch)
        }.toString()
    }

    override fun toString(): String {
        return "Monkey(id=$id, typewriter=$typewriter)"
    }

    abstract fun check(): MatchData

    open fun match(keystrokes: Int, index: Int): MatchData {
        matchKeystrokes.merge(keystrokes, 1) { prev, _ -> prev + 1 }
        addAttempt(keystrokes)
        return MatchData(true, keystrokes, index)
    }

    // keystroke value is the number of character *before* the space, i.e.,
    // the length of the non-matching word.
    fun noMatch(keystrokes: Int): MatchData {
        addAttempt(keystrokes)
        return MatchData(false, keystrokes, -1)
    }

    // number of keystrokes at which we'll hit the end-of-word character
    // thus length == 1 means we'll hit at the first invocation, with
    // an empty string, 8 means we had 7 (hypothetical) characters,
    // and so on and so forth.
    fun randomLength() = rand.nextRand()

    private fun addAttempt(keystrokes: Int) {
        numAttempts++
        totalKeystrokes += (keystrokes + 1)
    }
}
