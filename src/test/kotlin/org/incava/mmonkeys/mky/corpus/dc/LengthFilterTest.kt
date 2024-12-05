package org.incava.mmonkeys.mky.corpus.dc

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