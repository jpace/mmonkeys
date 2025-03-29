package org.incava.mmonkeys.mky.mind

import org.incava.mmonkeys.type.Keys

abstract class TypeStrategy {
    abstract fun getNextChar(): Char

    fun typeWord(): String {
        val sb = StringBuilder()
        var char = getNextChar()
        while (char != Keys.END_CHAR) {
            sb.append(char)
            char = getNextChar()
        }
        return sb.toString()
    }
}