package org.incava.mmonkeys.type

class DeterministicTypewriter(private val chars: List<Char>) : Typewriter(chars) {
    private var count = 0

    override fun nextChar(size: Int): Char {
        return chars[count++ % size]
    }
}