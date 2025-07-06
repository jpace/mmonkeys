package org.incava.mmonkeys.rand

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CharIntMap3Test {
    @Test
    fun increment() {
        val obj = CharIntMap3()
        obj.increment('a', 'b', 'c')
        assertEquals(1, obj.fetch('a', 'b', 'c'))
        obj.increment('a', 'b', 'c')
        assertEquals(2, obj.fetch('a', 'b', 'c'))
    }
}