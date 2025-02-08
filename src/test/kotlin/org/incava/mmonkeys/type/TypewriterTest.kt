package org.incava.mmonkeys.type

import kotlin.test.Test
import kotlin.test.assertEquals

internal class TypewriterTest {
    @Test
    fun chars() {
        val arg = ('a'..'z').toList()
        val obj = Typewriter(arg)
        assertEquals(arg, obj.chars)
    }
}