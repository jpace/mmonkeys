package org.incava.mmonkeys.trials.rand

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class LengthFilterTest {
    @Test
    fun checkLength() {
        val candidates = setOf("abc", "def", "ghi")
        val obj1 = LengthFilter(candidates)
        assertTrue { obj1.hasCandidates() }
    }

    @Test
    fun check() {
        val candidates = setOf("abc", "def", "ghi")
        val obj1 = LengthFilter(candidates)
        assertTrue { obj1.check('a') }
        assertTrue { obj1.check('b') }
        assertFalse { obj1.check('q') }
    }
}