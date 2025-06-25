package org.incava.mmonkeys.rand

class CharMap3<U> : Map3<Char, U>() {
    fun getRandomKey(): Char {
        return keys.random()
    }
}