package org.incava.mmonkeys.chars

class CharCount(val char: Char, val count: Int) : CountElement {
    override fun char() = char
    override fun count() = count

    override fun toString(): String {
        return "CharCount(char=$char, count=$count)"
    }
}
