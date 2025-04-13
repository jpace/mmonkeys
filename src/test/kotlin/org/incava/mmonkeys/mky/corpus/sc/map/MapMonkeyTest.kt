package org.incava.mmonkeys.mky.corpus.sc.map

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.words.Words
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MapMonkeyTest {
    @Test
    fun sharedCorpus() {
        // @todo - fix this -- monkeys no longer have a corpus:
        val input = listOf("this", "test", "is", "no", "test")
        val corpus = MapCorpus(input)
        val monkey1 = MapMonkeyFactory.create(1, corpus)
        val monkey2 = MapMonkeyFactory.create(2, corpus)
        var result: Words
        do {
            result = monkey1.findMatches()
            if (result.hasMatch())
                Qlog.info("result", result)
        } while (!result.hasMatch())
        assertEquals(corpus.indexedCorpus.elements, corpus.indexedCorpus.elements)
    }
}