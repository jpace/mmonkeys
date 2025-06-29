package org.incava.mmonkeys.corpus.dc

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.type.Chars
import org.incava.mmonkeys.util.ResourceUtil
import org.incava.rando.RandSlotsFactory
import kotlin.test.Test
import kotlin.test.assertTrue

internal class WordsGeneratorTest {
    @Test
    fun attemptMatch() {
        var count = 0
        val corpus = DualCorpus(CorpusFactory.fileToWords(ResourceUtil.FULL_FILE).filter { it.isNotEmpty() && ++count < 1000 })
        val slots = RandSlotsFactory.calcArray(Chars.NUM_ALL_CHARS, 128, 100_000)
        val obj = WordsGenerator(corpus, slots)
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
