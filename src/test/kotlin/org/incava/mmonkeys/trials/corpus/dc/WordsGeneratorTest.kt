package org.incava.mmonkeys.trials.corpus.dc

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.dc.DualCorpus
import org.incava.mmonkeys.mky.corpus.dc.WordsGeneratorFactory
import org.incava.mmonkeys.util.ResourceUtil
import kotlin.test.Test
import kotlin.test.assertTrue

internal class WordsGeneratorTest {
    @Test
    fun attemptMatch() {
        var count = 0
        val corpus = DualCorpus(CorpusFactory.fileToWords(ResourceUtil.FULL_FILE).filter { it.isNotEmpty() && ++count < 1000 })
        val obj = WordsGeneratorFactory.createWithDefaults(corpus)
        var foundMatch = false
        var attempt = 0
        while (!foundMatch && attempt++ < 1000) {
            val result = obj.runAttempts()
            Console.info("result", result)
            foundMatch = result.words.isNotEmpty()
        }
        assertTrue(foundMatch)
    }
}
