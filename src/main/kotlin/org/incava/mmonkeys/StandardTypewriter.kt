package org.incava.mmonkeys

import kotlin.random.Random

open class StandardTypewriter(private val characters: List<Char>) : Typewriter {
    private val numChars: Int = characters.size

    override fun nextCharacter(): Char {
        val idx = Random.nextInt(numChars)
        return characters[idx]
    }

    override fun toString(): String {
        return "StandardTypewriter(characters=$characters, numChars=$numChars)"
    }
}