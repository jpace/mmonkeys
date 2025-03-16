package org.incava.mmonkeys.mky.corpus.sc

import org.incava.mmonkeys.mky.corpus.type.TypeStrategy
import org.incava.mmonkeys.type.Keys
import kotlin.random.Random

class RandomStrategy(private val chars: List<Char> = Keys.fullList()) : TypeStrategy() {
    private val numChars: Int = chars.size

    fun numChars() = numChars

    override fun typeCharacter(): Char {
        return nextChar(numChars)
    }

    private fun nextChar(size: Int): Char {
        val idx = Random.nextInt(size)
        return chars[idx]
    }
}