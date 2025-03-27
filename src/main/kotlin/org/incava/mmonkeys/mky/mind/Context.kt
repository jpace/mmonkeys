package org.incava.mmonkeys.mky.mind

class Context(private val limit: Int) {
    val chars: MutableList<Char> = mutableListOf()

    val size: Int
        get() = chars.size

    fun charAt(index: Int): Char {
        val idx = if (index < 0) chars.size + index else index
        return chars[idx]
    }

    fun add(char: Char) {
        if (chars.size + 1 > limit) {
            chars.removeAt(0)
        }
        chars.add(char)
    }

    override fun toString(): String {
        return "Context(limit=$limit, chars=$chars)"
    }
}