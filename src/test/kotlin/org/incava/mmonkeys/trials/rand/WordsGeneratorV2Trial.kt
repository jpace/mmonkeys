package org.incava.mmonkeys.trials.rand

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.DualCorpus
import org.incava.mmonkeys.mky.corpus.MapCorpus
import org.incava.mmonkeys.mky.number.NumberedCorpus
import org.incava.mmonkeys.testutil.ResourceUtil
import org.incava.rando.RandIntsFactory
import org.incava.rando.RandSlotsFactory
import org.incava.time.Durations.measureDuration

class WordsGeneratorV2Trial {
    fun runTest(corpus: Corpus, wordsGenerator: WordsGeneratorV2) {
        var numMatched = 0
        var keystrokes = 0L
        val numToMatch = 250_000L
        val minLength = 4
        var longerMatched = 0
        val matchedByLength = sortedMapOf<Int, Int>()
        val bySize = corpus.words.groupBy { it.length }.mapValues { it.value.size }
        println("by size:")
        println(bySize.toSortedMap())
        Console.printf("total: %,d", bySize.values.sum())

        val duration = measureDuration {
            while (longerMatched < numToMatch && !corpus.isEmpty()) {
                val result = wordsGenerator.getWords()
                keystrokes += result.totalKeyStrokes
                result.strings.forEach { word ->
                    matchedByLength.compute(word.length) { _, value -> if (value == null) 1 else value + 1 }
                    ++numMatched
                    if (word.length > minLength) {
                        ++longerMatched
                        if (longerMatched % 1000 == 0) {
                            println("by size: ")
                            println(bySize.toSortedMap())
                            Console.printf("total: %,d", bySize.values.sum())
                            println()
                        }
                    }
                    WordsTrialBase.showCurrent(numMatched, longerMatched, matchedByLength)
//                    if (word.length > 13) {
//                        // MapCorpus needs to be updated; the filter/generator doesn't do that yet. (@todo)
//                        corpus.matched(word, word.length)
//                    }
                }
            }
        }
        Console.info("duration", duration)
    }
}

fun main() {
    val slots = RandSlotsFactory.calcArray(StrRand.Constants.NUM_CHARS + 1, 128, 100_000)
    val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE, -1)
    val obj = WordsGeneratorV2Trial()
    val dualCorpus = DualCorpus(words)
    val generator2 = WordsGeneratorV2(dualCorpus, slots, RandIntsFactory::nextInts2) { LengthFilter(dualCorpus, it) }
    obj.runTest(dualCorpus, generator2)
}