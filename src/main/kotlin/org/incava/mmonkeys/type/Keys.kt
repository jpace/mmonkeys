package org.incava.mmonkeys.type

object Keys {
    const val END_CHAR = ' '

    fun keyList(toChar: Char): List<Char> {
        return ('a'..toChar).toList() + END_CHAR
    }

    fun fullList(): List<Char> {
        return keyList('z')
    }
}