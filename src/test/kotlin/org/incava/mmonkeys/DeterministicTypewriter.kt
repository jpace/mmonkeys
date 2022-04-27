package org.incava.mmonkeys

class DeterministicTypewriter(private val characters: List<Char>) : Typewriter(characters) {
    private var count = 0

    override fun randomCharacter(): Char {
        return characters[count++ % numChars]
    }
}