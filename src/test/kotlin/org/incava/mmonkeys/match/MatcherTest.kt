package org.incava.mmonkeys.match

import org.junit.jupiter.api.Assertions
import kotlin.test.assertTrue

open class MatcherTest {
    fun runTest(stringMatcher: Matcher, expected: Long) {
        val result = stringMatcher.run()
        Assertions.assertEquals(expected, result)
        val iteration = stringMatcher.iteration
        assertTrue(iteration >= 0)
    }
}