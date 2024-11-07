package org.incava.mmonkeys.trials.rand

import org.incava.ikdk.io.Console
import org.incava.ikdk.util.MapUtil
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.DualCorpus
import org.incava.mmonkeys.testutil.ResourceUtil
import org.incava.time.Durations.measureDuration
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class WordsGeneratorV2Trial {
    val pattern = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")

    fun runTest(corpus: Corpus, wordsGenerator: WordsGeneratorV2) {
        val view = CorpusMatchesView(corpus)
        val startTime: ZonedDateTime = ZonedDateTime.of(0, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC"))
        var numMatched = 0
        var keystrokes = 0L
        val numToMatch = 100L
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
                    MapUtil.increment(matchedByLength, word.length)
                    ++numMatched
                    if (word.length > minLength) {
                        ++longerMatched
                        if (longerMatched % 100 == 0) {
                            view.showSimulationTime(startTime, keystrokes)
                            view.showMatchesByLength()
                            println("by size: ")
                            println(bySize.toSortedMap())
                            Console.printf("total: %,d", bySize.values.sum())
                            println()
                        }
                    }
                    // view.showWordsAsList(true)
                    WordsTrialBase.showCurrent(numMatched, longerMatched, matchedByLength)
                }
            }
        }
        Console.info("duration", duration)
    }

    fun showStatus(keystrokes: Long, startTime: ZonedDateTime) {
        Console.printf("actual time: %s", ZonedDateTime.now().format(pattern))
        val virtualDateTime = startTime.plusSeconds(keystrokes)
        Console.printf("virtual time: %s", virtualDateTime.format(pattern))
        Console.printf("current keystrokes: %,d", keystrokes)
        println()
    }

    fun showCorpus(corpus: Corpus) {
        val now = ZonedDateTime.now()
        println(now.format(pattern))
        corpus.words.withIndex().forEach { (index, word) ->
            println("$index - $word - ${corpus.matched.contains(index)}")
        }
    }
}

fun main() {
    val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)
    val obj = WordsGeneratorV2Trial()
    val corpus = DualCorpus(words)
    val generator2 = WordsGeneratorFactory.createWithDefaults(corpus)
    obj.runTest(corpus, generator2)
}