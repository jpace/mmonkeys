package org.incava.mmonkeys.trials.rand

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.DualCorpus
import org.incava.mmonkeys.testutil.ResourceUtil
import kotlin.test.Test
import kotlin.test.assertTrue

internal class WordsGeneratorTest {
    @Test
    fun getWords() {
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE).subList(0, 80)
        val corpus = DualCorpus(words)
        val obj = WordsGeneratorFactory.createWithDefaults(corpus)
        val lengthToCount = words.groupBy { it.length }.mapValues { it.value.size }
        Console.info("byLengths", lengthToCount.toSortedMap())
        var foundMatch = false
        var attempt = 0
        while (!foundMatch && attempt++ < 1000) {
            val result = obj.getWords()
            Console.info("result", result)
            foundMatch = result.words.isNotEmpty()
        }
        assertTrue(foundMatch)
    }
}
