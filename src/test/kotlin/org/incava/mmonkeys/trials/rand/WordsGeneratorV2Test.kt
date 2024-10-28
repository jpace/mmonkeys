package org.incava.mmonkeys.trials.rand

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.DualCorpus
import org.incava.mmonkeys.mky.corpus.MapCorpus
import org.incava.mmonkeys.mky.number.NumberedCorpus
import org.incava.mmonkeys.testutil.ResourceUtil
import org.incava.rando.RandIntsFactory
import org.incava.rando.RandSlotsFactory
import org.incava.time.Durations.measureDuration
import kotlin.test.Test

internal class WordsGeneratorV2Test {
    @Test
    fun getWords() {
        var numMatched = 0
        var keystrokes = 0L
        val numToMatch = 10L
        val minLength = 3
        var longerMatched = 0
        val matchedByLength = sortedMapOf<Int, Int>()
        val slots = RandSlotsFactory.calcArray(StrRand.Constants.NUM_CHARS + 1, 128, 100_000)
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE, -1)
        val dualCorpus = DualCorpus(words)
        val obj = WordsGeneratorV2(dualCorpus, slots, RandIntsFactory::nextInts2) { LengthFilter(dualCorpus, it) }
        val lengthToCount = words.groupBy { it.length }.mapValues { it.value.size }
        Console.info("byLengths", lengthToCount.toSortedMap())

        val duration = measureDuration {
            while (longerMatched < numToMatch && !dualCorpus.isEmpty()) {
                val result = obj.getWords()
                keystrokes += result.totalKeyStrokes
                result.strings.forEach { word ->
                    Console.info("word", word)
                    matchedByLength.compute(word.length) { _, value -> if (value == null) 1 else value + 1 }
                    ++numMatched
                    if (word.length > minLength) {
                        ++longerMatched
                    }
                    WordsTrialBase.showCurrent(numMatched, longerMatched, matchedByLength)
                    if (word.length > 13) {
                        // MapCorpus needs to be updated; the filter/generator doesn't do that yet. (@todo)
                        // corpus.matched(word, word.length)
                    }
                }
            }
        }
        Console.info("duration", duration)
    }
}
