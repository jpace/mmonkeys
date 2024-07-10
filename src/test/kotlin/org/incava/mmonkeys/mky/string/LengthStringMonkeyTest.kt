package org.incava.mmonkeys.mky.string

import org.incava.mmonkeys.type.DeterministicTypewriter
import org.incava.mmonkeys.type.Keys
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

internal class LengthStringMonkeyTest {
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