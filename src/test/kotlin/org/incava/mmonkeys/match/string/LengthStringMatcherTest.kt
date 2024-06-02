package org.incava.mmonkeys.match.string

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.MonkeyFactory
import org.incava.mmonkeys.match.MatcherTest
import org.incava.mmonkeys.testutil.MonkeyUtils
import org.incava.mmonkeys.type.DeterministicTypewriter
import org.incava.mmonkeys.type.Keys
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

internal class LengthStringMatcherTest : MatcherTest() {
    @Test
    fun check() {
        val (monkey, obj) = createMatcher("abcde")
        repeat(100) {
            val result = obj.check()
            assertNotNull(result, "it: $it")
        }
    }

    private fun createMatcher(sought: String, chars: List<Char> = Keys.keyList('e')): Pair<Monkey, StringMatcher> {
        val typewriter = DeterministicTypewriter(chars)
        val monkeyFactory = MonkeyFactory({ typewriter }, stringMatcher = ::LengthStringMatcher, chars = chars)
        return monkeyFactory.createStringMatcher(sought)
    }
}