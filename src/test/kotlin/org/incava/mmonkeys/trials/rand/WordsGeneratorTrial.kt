package org.incava.mmonkeys.trials.rand

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.MapCorpus
import org.incava.mmonkeys.testutil.ResourceUtil
import org.incava.rando.RandIntsFactory
import org.incava.rando.RandSlotsFactory
import org.incava.time.Durations.measureDuration

class WordsGeneratorTrial {
    fun runTest(corpus: MapCorpus, wordsGenerator: WordsGenerator) {
        var numMatched = 0L
        var keystrokes = 0L
        val numToMatch = 1000L
        val minLength = 3

        val duration = measureDuration {
            while (numMatched < numToMatch && corpus.lengthToStringsToIndices.isNotEmpty()) {
                val result = wordsGenerator.getWords()
                keystrokes += result.totalKeyStrokes
                result.strings.forEach { word ->
                    corpus.matched(word, word.length)
                    if (word.length > minLength) {
                        System.out.printf("%12.12s ", word)
                        ++numMatched
                        if (numMatched > 0 && numMatched % 10L == 0L) {
                            print(" -> $numMatched")
                            System.out.printf(" -> %,d\n", keystrokes)
                            val str = corpus.lengthToStringsToIndices.keys.sorted().joinToString(", ") { length ->
                                val count = corpus.lengthToStringsToIndices[length]?.size ?: ""
                                "$length -> $count"
                            }
                            println(str)
                        }
                    }
                }
            }
        }
        Console.info("duration", duration)
    }
}

fun main() {
    val slots = RandSlotsFactory.calcArray(StrRand.Constants.NUM_CHARS + 1, 128, 100_000)
    val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE, -1)
    val corpus = MapCorpus(words)
    Console.info("mapCorpus.keys", corpus.lengthToStringsToIndices.keys.sorted())
    val obj = WordsGeneratorTrial()
    val generator2 = WordsGenerator(slots, RandIntsFactory::nextInts2) { length -> LengthFilter(corpus, length) }
    obj.runTest(corpus, generator2)
}