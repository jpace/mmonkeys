package org.incava.mmonkeys.trials.rand

import org.incava.ikdk.io.Console
import org.incava.ikdk.io.Console.printf
import org.incava.ikdk.util.MapUtil
import org.incava.mmonkeys.corpus.Corpus
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class CorpusMatchesView(val corpus: Corpus) {
    private val fullPattern: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
    private val ymdPattern: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")

    fun showWordsAsList(onlyMatched: Boolean) {
        corpus.words().withIndex().forEach { (index, word) ->
            val matched = corpus.matches.isMatched(index)
            if (!onlyMatched || matched) {
                printf("%1s %3d - %s", if (matched) "+" else "", index, word)
            }
        }
    }

    fun showMatchesByLength() {
        val words = corpus.words()
        val matchedByLength = sortedMapOf<Int, Int>()
        words.indices
            .filter { index -> corpus.matches.isMatched(index) }
            .map { corpus.lengthAtIndex(it) }
            .forEach {
                MapUtil.increment(matchedByLength, it)
            }
        printf("%5s | %8s | %8s", "total", "words.#", "matched.#")
        val lengthToCount = words.groupBy { it.length }.mapValues { it.value.size }
        lengthToCount.toSortedMap().forEach { (length, count) ->
            printf("%5d | %,8d | %,8d", length, count, matchedByLength.getOrDefault(length, 0))
        }
        printf("%5s | %,8d | %,8d", "total", lengthToCount.values.sum(), matchedByLength.values.sum())
    }

    fun showSimulationTime(startTime: ZonedDateTime, keystrokes: Long) {
        val virtualDateTime = startTime.plusSeconds(keystrokes)
        Console.info("now", fullPattern.format(ZonedDateTime.now()))
        Console.info("virtualDateTime", fullPattern.format(virtualDateTime))
        printf("%16.16s | %24.24s | %,12d", dateTimeStr(ZonedDateTime.now()), dateTimeStr(virtualDateTime), keystrokes)
    }

    fun dateTimeStr(dateTime: ZonedDateTime) = ymdPattern.format(dateTime)
}