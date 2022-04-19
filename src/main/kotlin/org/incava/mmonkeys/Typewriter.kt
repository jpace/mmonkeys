package org.incava.mmonkeys

import kotlin.random.Random

class Typewriter(private val characters: List<Char>) {
    private val numChars: Int = characters.size

    fun randomCharacter(): Char {
        val idx = Random.nextInt(numChars)
        return characters[idx]
    }
}