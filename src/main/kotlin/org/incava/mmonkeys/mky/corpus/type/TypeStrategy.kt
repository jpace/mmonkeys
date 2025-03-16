package org.incava.mmonkeys.mky.corpus.type

import org.incava.mmonkeys.type.Keys

abstract class TypeStrategy {
    abstract fun typeCharacter(): Char

    open fun typeWord(): String {
        val builder = StringBuilder()
        while (true) {
            val ch = typeCharacter()
            if (ch == Keys.END_CHAR) {
                return builder.toString()
            } else {
                builder.append(ch)
            }
        }
    }
}