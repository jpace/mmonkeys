package org.incava.mmonkeys.chars

class CharCount(val char: Char, val count: Int) : CountElement {
    override fun char() = char
    override fun count() = count

    override fun toString(): String {
        return "CharCount(char=$char, count=$count)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CharCount

        if (char != other.char) return false
        if (count != other.count) return false

        return true
    }

    override fun hashCode(): Int {
        var result = char.hashCode()
        result = 31 * result + count
        return result
    }


}
