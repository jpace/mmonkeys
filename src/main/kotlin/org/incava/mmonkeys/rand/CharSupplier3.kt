package org.incava.mmonkeys.rand

interface CharSupplier3 : CharSupplier2 {
    fun getChar(firstChar: Char, secondChar: Char): Char
}