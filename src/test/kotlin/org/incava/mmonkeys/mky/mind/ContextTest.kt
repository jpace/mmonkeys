package org.incava.mmonkeys.mky.mind

import org.incava.ikdk.io.Qlog
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class ContextTest {

    @Test
    fun add() {
        val obj = Context(3)
        assertTrue(obj.chars.isEmpty())
        obj.add('j')
        assertEquals(listOf('j'), obj.chars)
        obj.add('e')
        assertEquals(listOf('j', 'e'), obj.chars)
        obj.add('n')
        assertEquals(listOf('j', 'e', 'n'), obj.chars)
        obj.add('f')
        assertEquals(listOf('e', 'n', 'f'), obj.chars)
    }

    @Test
    fun charAt() {
        val obj = Context(2)
        obj.add('a')
        assertEquals('a', obj.charAt(0))
        assertEquals('a', obj.charAt(-1))
        obj.add('b')
        assertEquals('b', obj.charAt(1))
        assertEquals('b', obj.charAt(-1))
        assertEquals('a', obj.charAt(-2))
    }
}