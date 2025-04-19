package org.incava.mmonkeys.type

import kotlin.test.Test
import kotlin.test.assertEquals

internal class ObservedTypewriterTest {
    @Test
    fun typeChars() {
        var calledWith: List<Char>? = null
        val observer: (List<Char>) -> Unit = { calledWith = it }
        val obj = ObservedTypewriter(Keys.fullList(), observer)
        obj.typeChars(listOf('t', 'e', 's', 't'))
        assertEquals(listOf('t', 'e', 's', 't'), calledWith)
    }
}