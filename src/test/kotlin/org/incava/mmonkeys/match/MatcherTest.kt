package org.incava.mmonkeys.match

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertTrue

open class MatcherTest {
    internal fun charList(first: Char, last: Char): List<Char> {
        return (first..last).toList() + ' '
    }

    fun run(stringMatcher: Matcher, expected: Long?) {
        if (expected == null) {
            assertThrows<RuntimeException> {
                stringMatcher.run(1000L)
            }
        } else {
            val result = stringMatcher.run(1000L)
            Assertions.assertEquals(expected, result)
            val iteration = stringMatcher.iteration
            assertTrue(iteration >= 0)
        }
    }
}