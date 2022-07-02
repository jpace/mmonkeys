package org.incava.mmonkeys.perf

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.StandardTypewriter
import org.incava.mmonkeys.Typewriter
import org.incava.mmonkeys.match.StringMatcher
import org.incava.mmonkeys.match.StringPartialMatcher
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

typealias MatcherCtor = (Typewriter, String) -> StringMatcher

@Disabled
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class PartialMatcherPerfTest {
    @Test
    fun runPerfTest() {
        val test = PerfTest()
        val supplier: MatcherCtor = { typ, str ->
            StringPartialMatcher(Monkey(38, typ), str)
        }
        listOf(
            "abc" to 10_000,
            "abcd" to 2_000,
            "abcde" to 400,
            "abcdeg" to 50,
        ).forEach { (sought, count) -> add(test, 'h', sought, supplier, count) }

        listOf(
            "abc" to 1_000,
            "abcd" to 1000,
            "abcde" to 100,
            "abcdef" to 1,
            "abcdefg" to 1,
            "abcdefgh" to 1,
            "abcdefghi" to 1,
        ).forEach { (sought, count) -> add(test, 'z', sought, supplier, count) }
    }

    private fun add(test: PerfTest, lastChar: Char, sought: String, ctor: MatcherCtor, count: Int) {
        val chars = ('a'..lastChar).toList() + ' '
        val typ = StandardTypewriter(chars)
        val matcher = ctor.invoke(typ, sought)
        test.addTrial(matcher, count)
    }
}
