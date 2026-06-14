package org.incava.mmonkeys.chars

data class Chars2(val first: Char, val second: Char)

infix fun Char.and(other: Char) = Chars2(this, other)

infix fun Chars2.and(third: Char) = Chars3(first, second, third)