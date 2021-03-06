package org.incava.mmonkeys.type

import kotlin.random.Random

open class StandardTypewriter(private val chars: List<Char> = ('a'..'z').toList() + ' ') : Typewriter {
    private val numChars: Int = chars.size

    override fun nextCharacter(): Char {
        val idx = Random.nextInt(numChars)
        return chars[idx]
    }

    override fun toString(): String {
        return "StandardTypewriter(chars=$chars, numChars=$numChars)"
    }
}