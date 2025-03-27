package org.incava.mmonkeys.mky.mind

import org.incava.mmonkeys.type.Keys

abstract class TypeStrategy {
    abstract fun getChar(): Char

    open fun typeWord(): String {
        val sb = StringBuilder()
        var char = getChar()
        while (char != Keys.END_CHAR) {
            sb.append(char)
            char = getChar()
        }
        return sb.toString()
    }
}