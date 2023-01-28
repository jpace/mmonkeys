package org.incava.mmonkeys.type

import kotlin.random.Random

open class StandardTypewriter(private val chars: List<Char> = Keys.fullList()) : Typewriter {
    private val numChars: Int = chars.size
    private val numWordChars: Int = chars.size - 1

    override fun numChars(): Int {
        return numChars
    }

    override fun nextCharacter(): Char {
        val idx = Random.nextInt(numChars)
        return chars[idx]
    }

    override fun nextWordCharacter(): Char {
        val idx = Random.nextInt(numWordChars)
        return chars[idx]
    }

    override fun toString(): String {
        return "StandardTypewriter(toChar=${chars[numChars - 2]}, numChars=$numChars)"
    }
}