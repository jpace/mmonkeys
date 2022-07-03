package org.incava.mmonkeys.type

class DeterministicTypewriter(private val characters: List<Char>) : Typewriter {
    private val numChars: Int = characters.size
    private var count = 0

    override fun nextCharacter(): Char {
        return characters[count++ % numChars]
    }

    override fun toString(): String {
        return "DeterministicTypewriter(characters=$characters, numChars=$numChars, count=$count)"
    }
}