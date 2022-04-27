package org.incava.mmonkeys

import kotlin.random.Random

open class Typewriter(private val characters: List<Char>) {
    val numChars: Int = characters.size

    open fun randomCharacter(): Char {
        val idx = Random.nextInt(numChars)
        return characters[idx]
    }
}