package org.incava.mmonkeys.mky.corpus

import org.incava.mmonkeys.words.Words
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test

internal class MapMonkeyTest {
    @Test
    fun sharedCorpus() {
        val input = listOf("this", "test", "is", "no", "test")
        val corpus = MapCorpus(input)
        val monkey1 = MapMonkeyUtils.createMapMonkey(1, corpus)
        val monkey2 = MapMonkeyUtils.createMapMonkey(2, corpus)
        var result: Words
        do {
            result = monkey1.findMatches()
        } while (!result.hasMatch())
        assertSame(monkey1.corpus, monkey2.corpus)
        assertEquals(corpus.indexedCorpus.elements, corpus.indexedCorpus.elements)
    }
}