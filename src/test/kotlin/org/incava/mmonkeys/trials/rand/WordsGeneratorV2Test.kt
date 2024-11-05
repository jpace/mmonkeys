package org.incava.mmonkeys.trials.rand

import org.incava.ikdk.io.Console
import org.incava.ikdk.io.Console.printf
import org.incava.ikdk.util.MapUtil
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.DualCorpus
import org.incava.mmonkeys.testutil.ResourceUtil
import org.incava.time.Durations.measureDuration
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.test.Test

internal class WordsGeneratorV2Test {
    val pattern = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")

    @Test
    fun getWords() {
        var numMatched = 0
        var keystrokes = 0L
        val numToMatch = 10L
        val minLength = 3
        var longerMatched = 0
        val matchedByLength = sortedMapOf<Int, Int>()
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE).subList(0, 80)
        val corpus = DualCorpus(words)
        val view = CorpusMatchesView(corpus)
        val obj = WordsGeneratorFactory.createWithDefaults(corpus)
        val lengthToCount = words.groupBy { it.length }.mapValues { it.value.size }
        Console.info("byLengths", lengthToCount.toSortedMap())
        val startTime: ZonedDateTime = ZonedDateTime.of(0, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC"))
        val duration = measureDuration {
            while (longerMatched < numToMatch && !corpus.isEmpty()) {
                val result = obj.getWords()
                keystrokes += result.totalKeyStrokes
                result.strings.withIndex().forEach { (index, word) ->
                    Console.info("word", word)
                    MapUtil.increment(matchedByLength, word.length)
                    ++numMatched
                    if (word.length > minLength) {
                        ++longerMatched
                    }
                    // or, iterator.isLast():
                    if (index == result.strings.size - 1) {
                        view.showSimulationTime(startTime, keystrokes)
                        // view.showWordsAsList()
                        view.showMatchesByLength()
                        showStatus(keystrokes, startTime, corpus)
                    }
                }
            }
        }
        Console.info("duration", duration)
    }

    fun showStatus(keystrokes: Long, startTime: ZonedDateTime, corpus: Corpus) {
        printf("action time: %s", ZonedDateTime.now().format(pattern))
        val virtualDateTime = startTime.plusSeconds(keystrokes)
        printf("virtual time: %s", virtualDateTime.format(pattern))
        printf("current keystrokes: %,d", keystrokes)
        // showCorpus(corpus)
        println()
    }

    fun showCorpus(corpus: Corpus) {
        val now = ZonedDateTime.now()
        println(now.format(pattern))
        corpus.words.withIndex().forEach { (index, word) ->
            if (index > 0) {
                if (index % 10 == 0) {
                    println()
                } else {
                    print(' ')
                }
            }
            if (corpus.matched.contains(index)) {
                print(word)
            } else {
                val blank = "-".repeat(word.length)
                print(blank)
            }
        }
        println()
    }
}
