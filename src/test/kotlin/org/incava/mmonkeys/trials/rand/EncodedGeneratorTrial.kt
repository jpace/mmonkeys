package org.incava.mmonkeys.trials.rand

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.DualCorpus
import org.incava.mmonkeys.testutil.ResourceUtil
import org.incava.time.Durations.measureDuration

class EncodedGeneratorTrial {
    fun runTest(corpus: DualCorpus, generator: EncodedGenerator) {
        var numMatched = 0L
        val numToMatch = 1000L
        val duration = measureDuration {
            while (numMatched < numToMatch && corpus.hasUnmatched()) {
                val result = generator.getWord(7)
                if (result != null) {
                    ++numMatched
                }
            }
        }
        Console.info("duration", duration)
    }
}

fun main() {
    val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)
    val corpus = DualCorpus(words)
    Console.info("corpus", corpus)
    val obj = EncodedGeneratorTrial()
    val generator2 = EncodedGenerator(corpus)
    obj.runTest(corpus, generator2)
}