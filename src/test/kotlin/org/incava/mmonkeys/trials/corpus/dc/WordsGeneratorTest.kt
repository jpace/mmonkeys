package org.incava.mmonkeys.trials.corpus.dc

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.dc.WordsGeneratorFactory
import org.incava.mmonkeys.testutil.ResourceUtil
import kotlin.test.Test
import kotlin.test.assertTrue

internal class WordsGeneratorTest {
    @Test
    fun getWords() {
        var count = 0
        val corpus = CorpusFactory.dualCorpusOf(ResourceUtil.FULL_FILE) { it.length > 1 && ++count < 1000 }
        val obj = WordsGeneratorFactory.createWithDefaults(corpus)
        var foundMatch = false
        var attempt = 0
        while (!foundMatch && attempt++ < 1000) {
            val result = obj.findMatches()
            Console.info("result", result)
            foundMatch = result.hasMatch()
        }
        assertTrue(foundMatch)
    }
}
