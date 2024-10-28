package org.incava.mmonkeys.trials.rand

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.DualCorpus
import org.incava.mmonkeys.testutil.ResourceUtil
import org.incava.time.Durations.measureDuration

class EncodedGeneratorTrial {
    fun runTest(corpus: Corpus, generator: EncodedGenerator) {
        var numMatched = 0L
        var keystrokes = 0L
        val numToMatch = 1000L
        val minLength = 3

        val duration = measureDuration {
            while (numMatched < numToMatch && !corpus.isEmpty()) {
                val result = generator.getWord(1)
                if (result != null) {
                    Console.info("result", result)
                    corpus.match(result)
                    ++numMatched
                }
            }
        }
        Console.info("duration", duration)
    }
}

fun main() {
    val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE, -1)
    val corpus = DualCorpus(words)
    Console.info("corpus", corpus)
    val obj = EncodedGeneratorTrial()
    val generator2 = EncodedGenerator(corpus)
    obj.runTest(corpus, generator2)
}