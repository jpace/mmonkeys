package org.incava.mmonkeys.match

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertTrue

open class MatcherTest {
    internal fun charList(first: Char, last: Char): List<Char> {
        return (first..last).toList() + ' '
    }

    fun run(matcher: Matcher, expected: Long?) {
        if (expected == null) {
            assertThrows<RuntimeException> {
                matcher.run(1000L)
            }
        } else {
            val result = matcher.run(1000L)
            Assertions.assertEquals(expected, result)
            val iteration = matcher.iteration
            assertTrue(iteration >= 0)
        }
    }
}