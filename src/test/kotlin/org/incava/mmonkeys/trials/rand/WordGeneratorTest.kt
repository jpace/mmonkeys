package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.MapCorpus
import org.incava.mmonkeys.testutil.ResourceUtil
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class WordGeneratorTest {
    @Test
    fun generate() {
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE, -1)
        val corpus = MapCorpus(words)
        val obj = WordGenerator(corpus)
        val result = obj.generate()
        // first might be -1 (no match), or 0 and above:
        assertTrue(result.second > 0)
    }
}