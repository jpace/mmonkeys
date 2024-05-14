package org.incava.mmonkeys.match.string

import org.incava.mmonkeys.match.MatcherTest
import org.incava.mmonkeys.testutil.MonkeyUtils
import org.incava.mmonkeys.type.Keys
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

internal class LengthStringMatcherTest : MatcherTest() {
    @Test
    fun check() {
        val monkey = MonkeyUtils.createDeterministicMonkey(Keys.keyList('e'))
        val obj = LengthStringMatcher(monkey, "abcde")
        repeat(100) {
            val result = obj.check()
            assertNotNull(result, "it: $it")
        }
    }
}