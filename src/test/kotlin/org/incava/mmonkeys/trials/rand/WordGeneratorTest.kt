package org.incava.mmonkeys.trials.rand

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.MapCorpus
import org.incava.mmonkeys.testutil.ResourceUtil
import org.junit.jupiter.api.Test

internal class WordGeneratorTest {
    @Test
    fun generate1() {
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE, -1)
        val corpus = MapCorpus(words)
        val obj = WordGenerator(corpus)
        repeat(10_000) {
            val result = obj.generate()
            if (result.first >= 0 && result.second > 2) {
                val word = corpus.words[result.first]
                Console.info("word", word)
            }
        }
    }
}