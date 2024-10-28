package org.incava.mmonkeys.trials.rand

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.MapCorpus
import org.incava.mmonkeys.mky.number.NumberedCorpus
import org.incava.mmonkeys.testutil.ResourceUtil
import org.incava.rando.RandIntsFactory
import org.incava.rando.RandSlotsFactory
import org.incava.time.Durations.measureDuration

class WordsGeneratorV2Trial {
    fun runTest(corpus: MapCorpus, wordsGenerator: WordsGeneratorV2) {
        var numMatched = 0
        var keystrokes = 0L
        val numToMatch = 10_000L
        val minLength = 3
        var longerMatched = 0
        val matchedByLength = sortedMapOf<Int, Int>()

        val duration = measureDuration {
            while (longerMatched < numToMatch && corpus.lengthToStringsToIndices.isNotEmpty()) {
                val result = wordsGenerator.getWords()
                keystrokes += result.totalKeyStrokes
                result.strings.forEach { word ->
                    matchedByLength.compute(word.length) { _, value -> if (value == null) 1 else value + 1 }
                    ++numMatched
                    if (word.length > minLength) {
                        ++longerMatched
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
    val corpus = MapCorpus(words)
    Console.info("mapCorpus.keys", corpus.lengthToStringsToIndices.keys.sorted())
    val obj = WordsGeneratorV2Trial()
    val shortWords = words.filter { it.length <= 13 }
    val longWords = words.filter { it.length > 13 }
    val numberedCorpus = NumberedCorpus(shortWords)
    val mapCorpus = MapCorpus(longWords)
    val generator2 = WordsGeneratorV2(mapCorpus, numberedCorpus, slots, RandIntsFactory::nextInts2) { LengthFilter(mapCorpus, it) }
    obj.runTest(corpus, generator2)
}