package org.incava.mmonkeys.mky.corpus

import org.incava.mmonkeys.mky.MatchData
import org.incava.mmonkeys.type.Typewriter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test

internal class MapMonkeyTest {
    @Test
    fun sharedCorpus() {
        val input = listOf("this", "test", "is", "no", "test")
        val corpus = MapCorpus(input)
        val monkey1 = makeMonkey(1, corpus)
        val monkey2 = makeMonkey(2, corpus)
        var result: MatchData
        do {
            result = monkey1.check()
        } while (!result.isMatch)
        assertSame(monkey1.corpus, monkey2.corpus)
        assertEquals(corpus.indexedCorpus.elements, corpus.indexedCorpus.elements)
    }

    private fun makeMonkey(id: Int, corpus: MapCorpus): MapMonkey {
        val typewriter = Typewriter()
        return MapMonkey(id, typewriter, corpus)
    }
}