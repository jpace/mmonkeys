package org.incava.mmonkeys.mky.corpus

import org.incava.mmonkeys.mky.MatchData
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.testutil.MonkeyUtils
import org.incava.mmonkeys.type.DeterministicTypewriter
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test

internal class MapMonkeyTest {
    @Test
    fun sharedCorpus() {
        val input = listOf("this", "test", "is", "no", "test")
        val corpus = MapCorpus(input)
        val monkey1 = makeMonkey(corpus) as MapMonkey
        val monkey2 = makeMonkey(corpus) as MapMonkey
        var result: MatchData
        do {
            result = monkey1.check()
        } while (!result.isMatch)
        assertSame(monkey1.corpus, monkey2.corpus)
        assertEquals(corpus.indexedCorpus.elements, corpus.indexedCorpus.elements)
    }

    @Test
    fun nextChars() {
        val typewriter = DeterministicTypewriter(Keys.fullList())
        val obj = MapMonkey(37, typewriter, MapCorpus(listOf("")))
        val result = obj.nextChars(6)
        kotlin.test.assertEquals("abcdef", result)
    }

    private fun makeMonkey(corpus: MapCorpus): Monkey {
        return MonkeyUtils.createMonkey(corpus, ::MapMonkey, typewriterCtor = { Typewriter(it) })
    }
}