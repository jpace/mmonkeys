package org.incava.mmonkeys.match.string

import org.incava.mmonkeys.match.MatcherTest
import org.incava.mmonkeys.testutil.MonkeyUtils
import org.incava.mmonkeys.type.DeterministicTypewriter
import org.incava.mmonkeys.type.Keys
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

internal class LengthStringMonkeyTest : MatcherTest() {
    @Test
    fun check() {
        val typewriter = DeterministicTypewriter(Keys.keyList('e'))
        val obj = LengthStringMonkey("abcde", 1, typewriter)
        repeat(100) {
            val result = obj.check()
            assertNotNull(result, "it: $it")
        }
    }
}