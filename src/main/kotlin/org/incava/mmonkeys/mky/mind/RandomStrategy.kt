package org.incava.mmonkeys.mky.mind

import kotlin.random.Random

class RandomStrategy(val chars: List<Char>) : TypeStrategy() {
    val numChars: Int = chars.size

    override fun getChar(): Char {
        val idx = Random.nextInt(numChars)
        return chars[idx]
    }
}