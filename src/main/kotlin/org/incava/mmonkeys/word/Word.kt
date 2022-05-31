package org.incava.mmonkeys.word

class Word() {
    constructor(char: Char) : this() {
        characters.add(char)
    }

    constructor(string: String): this() {
        val chars = string.toCharArray().toTypedArray()
        characters.addAll(chars)
    }

    constructor(chars: List<Char>): this() {
        characters.addAll(chars)
    }

    var characters: MutableList<Char> = mutableListOf()

    operator fun plusAssign(ch: Char) {
        characters.add(ch)
    }

    fun length(): Int {
        return characters.size
    }

    override fun toString(): String {
        return "$characters"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Word
        if (characters != other.characters) return false
        return true
    }

    override fun hashCode(): Int {
        return characters.hashCode()
    }
}
