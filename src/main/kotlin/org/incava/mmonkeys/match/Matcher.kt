package org.incava.mmonkeys.match

import org.incava.mmonkeys.Monkey

abstract class Matcher(private val monkey: Monkey) : Matching {
    // @todo - integrate this into Monkey class

    override fun match(keystrokes: Int, index: Int): MatchData = monkey.match(keystrokes, index)

    override fun noMatch(keystrokes: Int): MatchData = monkey.noMatch(keystrokes)

    override fun randomLength() = monkey.randomLength()

    override fun toString() = "monkey.id: ${monkey.id}"

    fun nextString() = monkey.nextString()

    fun nextChars(length: Int) = monkey.nextChars(length)

    fun nextChar() = monkey.nextChar()
}