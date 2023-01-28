package org.incava.mmonkeys.type

interface Typewriter {
    fun numChars(): Int

    fun nextCharacter(): Char

    fun nextWordCharacter(): Char
}