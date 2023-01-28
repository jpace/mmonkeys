package org.incava.mmonkeys.type

class DeterministicTypewriter(private val chars: List<Char>) : Typewriter {
    private val numChars: Int = chars.size
    private val numWordChars: Int = chars.size - 1
    private var count = 0

    override fun numChars(): Int {
        return numChars
    }

    override fun nextCharacter(): Char {
        return chars[count++ % numChars]
    }

    override fun nextWordCharacter(): Char {
        return chars[count++ % numWordChars]
    }

    override fun toString(): String {
        return "DeterministicTypewriter(chars=$chars, numChars=$numChars, count=$count)"
    }
}