package org.incava.mmonkeys.trials.rand

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.MapCorpus
import org.incava.mmonkeys.testutil.ResourceUtil
import org.incava.rando.RandSlotsFactory
import org.junit.jupiter.api.Test

internal class KnownWordFilterTest {
    @Test
    fun check() {
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE, -1)
        val corpus = MapCorpus(words)
        val obj1 = KnownWordFilter(corpus, 5)
        listOf('c', 'h', 'e', 'c', 'k').forEach { ch ->
            val result = obj1.check(ch)
            Console.info("result", result)
        }
        val obj2 = KnownWordFilter(corpus, 5)
        listOf('c', 'g', 'e', 'c', 'k').forEach { ch ->
            val result = obj2.check(ch)
            Console.info("result", result)
        }
    }

    @Test
    fun generate2() {
        val slots = RandSlotsFactory.calcArray(StrRand.Constants.NUM_CHARS + 1, 128, 100_000)
        val generator3 = StrRandFiltered(slots)
        val wordsGenerator3 = WordsGenerator(slots, generator3)
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE, -1)
        val mapCorpus = MapCorpus(words)
        repeat(100) {
            val result = wordsGenerator3.generate2 { KnownWordFilter(mapCorpus, it) }
            // Console.info("result", result)
            Console.info("result.strings", result.strings)
        }
    }
}