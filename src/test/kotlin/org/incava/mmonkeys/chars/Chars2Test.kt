package org.incava.mmonkeys.chars

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Chars2Test {
    @Test
    fun getA() {
        val obj = Chars2('a', 'b')
        assertEquals('a', obj.first)
    }

    @Test
    fun getB() {
        val obj = Chars2('a', 'b')
        assertEquals('b', obj.second)
    }

    @Test
    fun and() {
        val obj = 'x' and 'y'
        assertEquals('x', obj.first)
        assertEquals('y', obj.second)
    }
}