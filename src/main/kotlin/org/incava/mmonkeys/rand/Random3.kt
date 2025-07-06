package org.incava.mmonkeys.rand

import org.incava.ikdk.io.Qlog

class Random3(val threes: CharIntMap3) {
    fun getChar(firstChar: Char, secondChar: Char): Char {
        val forFirst = threes.getValue(firstChar)
        val forSecond = forFirst.getValue(secondChar)
        return forSecond.keys.random()
    }

    fun getChar(firstChar: Char): Char {
        val forChar = threes.getValue(firstChar)
        return forChar.keys.random()
    }

    fun getChar(): Char {
        return threes.keys.random()
    }
}