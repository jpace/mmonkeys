package org.incava.mmonkeys.trials.corpus.dc

import org.incava.ikdk.io.Console
import org.incava.ikdk.util.MapUtil
import org.incava.mmonkeys.corpus.Corpus
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.dc.DualCorpus
import org.incava.mmonkeys.mky.corpus.dc.WordsGenerator
import org.incava.mmonkeys.mky.corpus.dc.WordsGeneratorFactory
import org.incava.mmonkeys.util.ResourceUtil
import org.incava.mmonkeys.trials.rand.CorpusMatchesView
import org.incava.time.Durations.measureDuration
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class WordsGeneratorTrial {
    val pattern = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")

    fun runTest(corpus: Corpus, wordsGenerator: WordsGenerator) {
        val view = CorpusMatchesView(corpus)
        val startTime: ZonedDateTime = ZonedDateTime.of(0, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC"))
        var numMatched = 0
        var keystrokes = 0L
        val numToMatch = 100_000L
        val minLength = 4
        var longerMatched = 0
        val matchedByLength = sortedMapOf<Int, Int>()
        val bySize = corpus.words().groupBy { it.length }.mapValues { it.value.size }
        Console.info("by size", bySize.toSortedMap())
        Console.info("total", bySize.values.sum())
        val matches = corpus.matches
        val duration = measureDuration {
            while (longerMatched < numToMatch && matches.count() < corpus.numWords()) {
                val result = wordsGenerator.runAttempts()
                keystrokes += result.totalKeyStrokes
                result.words.forEach { word ->
                    MapUtil.increment(matchedByLength, word.string.length)
                    ++numMatched
                    if (word.string.length > minLength) {
                        ++longerMatched
                        if (longerMatched % 100 == 0) {
                            Console.info("longerMatched", longerMatched)
                            view.showSimulationTime(startTime, keystrokes)
                            view.showMatchesByLength()
                            Console.info("by size", bySize.toSortedMap())
                            Console.printf("total: %,d", bySize.values.sum())
                            Console.info("matches", matchedByLength.toSortedMap())
                            println()
                        }
                    }
                    // view.showWordsAsList(true)
                    // WordsTrialUtil.showCurrent(numMatched, longerMatched, matchedByLength)
                }
            }
        }
        Console.info("duration", duration.second)
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
        corpus.words().withIndex().forEach { (index, word) ->
            println("$index - $word - ${corpus.matches.isMatched(index)}")
        }
    }
}

fun main() {
    val corpus = DualCorpus(CorpusFactory.fileToWords(ResourceUtil.FULL_FILE))
    val obj = WordsGeneratorTrial()
    val wordsGenerator = WordsGeneratorFactory.createWithDefaults(corpus)
    obj.runTest(corpus, wordsGenerator)
}