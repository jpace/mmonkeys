package org.incava.mmonkeys.trials.rand

import org.incava.ikdk.io.Console
import org.incava.ikdk.io.Console.printf
import org.incava.ikdk.util.MapUtil
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.MapCorpus
import org.incava.mmonkeys.testutil.ResourceUtil
import org.incava.mmonkeys.type.Chars
import org.incava.rando.RandIntsFactory
import org.incava.rando.RandSlotsFactory
import org.incava.time.Durations.measureDuration

class WordsGeneratorTrial {
    fun runTestOrig(corpus: MapCorpus, wordsGenerator: WordsGenerator) {
        var numMatched = 0L
        var keystrokes = 0L
        val numToMatch = 1000L
        val minLength = 3

        val duration = measureDuration {
            while (numMatched < numToMatch && corpus.lengths.isNotEmpty()) {
                val result = wordsGenerator.getWords()
                keystrokes += result.totalKeyStrokes
                result.strings.forEach { word ->
                    Console.info("word", word)
                    corpus.matched(word, word.length)
                    if (word.length > minLength) {
                        System.out.printf("%12.12s ", word)
                        ++numMatched
                        if (numMatched > 0 && numMatched % 10L == 0L) {
                            print(" -> $numMatched")
                            System.out.printf(" -> %,d\n", keystrokes)
                            val str = corpus.lengths.sorted().joinToString(", ") { length ->
                                val count = corpus.indexedCorpus.elements[length]?.size ?: ""
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

    fun runTest(corpus: MapCorpus, wordsGenerator: WordsGenerator) {
        var numMatched = 0
        var keystrokes = 0L
        val numToMatch = 1000L
        val minLength = 3
        var longerMatched = 0
        val matchedByLength = sortedMapOf<Int, Int>()

        val duration = measureDuration {
            while (longerMatched < numToMatch && corpus.lengths.isNotEmpty()) {
                val result = wordsGenerator.getWords()
                keystrokes += result.totalKeyStrokes
                result.strings.forEach { word ->
                    MapUtil.increment(matchedByLength, word.length)
                    ++numMatched
                    if (word.length > minLength) {
                        ++longerMatched
                    }
                    WordsTrialBase.showCurrent(numMatched, longerMatched, matchedByLength)
                    corpus.matched(word, word.length)
                }
            }
        }
        Console.info("duration", duration)
    }
}

fun main() {
    val slots = RandSlotsFactory.calcArray(Chars.NUM_ALL_CHARS, 128, 100_000)
    val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)
    val corpus = MapCorpus(words)
    Console.info("mapCorpus.lengths", corpus.lengths.sorted())
    val obj = WordsGeneratorTrial()
    val generator2 = WordsGenerator(slots, RandIntsFactory::nextInts2) { length -> LengthFilter(corpus, length) }
    obj.runTest(corpus, generator2)
}