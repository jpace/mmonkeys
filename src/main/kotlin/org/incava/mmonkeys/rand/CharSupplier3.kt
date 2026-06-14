package org.incava.mmonkeys.rand

import org.incava.mmonkeys.chars.Chars2

interface CharSupplier3 : CharSupplier2 {
    fun getChar(chars: Chars2): Char
}