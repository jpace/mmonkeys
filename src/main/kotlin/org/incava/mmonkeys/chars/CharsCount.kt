package org.incava.mmonkeys.chars

class CharsCount(val char: Char, val nextChars: List<CountElement>) : CountElement {
    override fun char(): Char = char
    override fun count(): Int {
        // @todo don't recalculate each time
        return nextChars.fold(0) { acc, it -> acc + it.count() }
    }

    override fun toString(): String {
        return "CharsCount(char=$char, nextChars=$nextChars)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CharsCount

        if (char != other.char) return false
        if (nextChars != other.nextChars) return false

        return true
    }

    override fun hashCode(): Int {
        var result = char.hashCode()
        result = 31 * result + nextChars.hashCode()
        return result
    }
}