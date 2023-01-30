package org.incava.mmonkeys.match

import org.junit.jupiter.api.Assertions
import kotlin.test.assertTrue

open class MatcherTest {
    fun runTest(matcher: Matcher, expected: Long) {
        val runner = MatcherRunner(matcher)
        val result = runner.run()
        Assertions.assertEquals(expected, result)
        val iteration = runner.iteration
        assertTrue(iteration >= 0)
    }
}