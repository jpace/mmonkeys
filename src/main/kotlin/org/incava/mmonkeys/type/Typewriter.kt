package org.incava.mmonkeys.type

import kotlin.random.Random

open class Typewriter(private val chars: List<Char> = Keys.fullList()) {
    private val numChars: Int = chars.size
    private val numWordChars: Int = chars.size - 1

    fun numChars() = numChars

    fun nextCharacter(): Char {
        return nextChar(numChars)
    }

    open fun nextWordCharacter(): Char {
        return nextChar(numWordChars)
    }

    open fun nextChar(size: Int): Char {
        val idx = Random.nextInt(size)
        return chars[idx]
    }
}