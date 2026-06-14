package org.incava.mmonkeys.chars

data class Chars3(val first: Char, val second: Char, val third: Char)

infix fun Chars3.and(fourth: Char) = Chars4(first, second, third, fourth)
