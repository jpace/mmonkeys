package org.incava.mmonkeys.type

import kotlin.test.Test
import kotlin.test.assertEquals

internal class ObservedTypewriterTest {
    @Test
    fun typeChars() {
        var charsCalledWith: List<Char>? = null
        var numCharsCalledWith: Int? = null
        val observer = object : TypewriterObserver() {
            override fun charsTyped(chars: List<Char>) {
                charsCalledWith = chars
            }

            override fun numCharsTyped(numChars: Int) {
                numCharsCalledWith = numChars
            }
        }
        val obj = ObservedTypewriter(observer)
        obj.typeChars(listOf('t', 'e', 's', 't'))
        obj.typeChars(42)
        assertEquals(listOf('t', 'e', 's', 't'), charsCalledWith)
        assertEquals(42, numCharsCalledWith)
    }
}