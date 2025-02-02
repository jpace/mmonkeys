package org.incava.mmonkeys.type

import kotlin.random.Random

open class DefaultTypewriter(private val chars: List<Char> = Keys.fullList()) : ITypewriter {
    private val numChars: Int = chars.size
    private val numWordChars: Int = chars.size - 1

    fun numChars() = numChars

    override fun nextCharacter(): Char {
        return nextChar(numChars)
    }

    override fun nextWordCharacter(): Char {
        return nextChar(numWordChars)
    }

    open fun nextChar(size: Int): Char {
        val idx = Random.nextInt(size)
        return chars[idx]
    }
}