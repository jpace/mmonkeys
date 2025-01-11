package org.incava.mmonkeys.type

object Keys {
    const val END_CHAR = ' '
    const val LAST_CHAR = 'z'

    fun keyList(toChar: Char): List<Char> {
        return ('a'..toChar).toList() + END_CHAR
    }

    fun fullList(toChar: Char = LAST_CHAR): List<Char> {
        return keyList(toChar)
    }

    fun alphaList(toChar: Char = LAST_CHAR): List<Char> {
        return ('a'..toChar).toList()
    }
}