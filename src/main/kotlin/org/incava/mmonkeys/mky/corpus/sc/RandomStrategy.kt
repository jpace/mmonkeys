package org.incava.mmonkeys.mky.corpus.sc

import org.incava.mmonkeys.type.Keys
import kotlin.random.Random

class RandomStrategy(private val chars: List<Char> = Keys.fullList()) {
    private val numChars: Int = chars.size
    private val numWordChars: Int = chars.size - 1

    fun numChars() = numChars

    fun nextCharacter(): Char {
        return nextChar(numChars)
    }

    fun nextWordCharacter(): Char {
        return nextChar(numWordChars)
    }

    private fun nextChar(size: Int): Char {
        val idx = Random.nextInt(size)
        return chars[idx]
    }
}