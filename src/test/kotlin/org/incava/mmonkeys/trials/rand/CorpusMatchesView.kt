package org.incava.mmonkeys.trials.rand

import org.incava.ikdk.io.Console.printf
import org.incava.ikdk.util.MapUtil
import org.incava.mmonkeys.mky.corpus.Corpus
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class CorpusMatchesView(val corpus: Corpus) {
    val pattern: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")

    fun showWordsAsList(onlyMatched: Boolean) {
        corpus.words.withIndex().forEach { (index, word) ->
            val matched = corpus.matched.contains(index)
            if (!onlyMatched || matched) {
                printf("%1s %3d - %s", if (matched) "+" else "", index, word)
            }
        }
    }

    fun showMatchesByLength() {
        val matchedByLength = sortedMapOf<Int, Int>()
        corpus.words.indices
            .filter { index -> corpus.isMatched(index) }
            .map { corpus.words[it].length }
            .forEach {
                MapUtil.increment(matchedByLength, it)
            }
        printf("total: %,d", matchedByLength.values.sum())
        val lengthToCount = corpus.words.groupBy { it.length }.mapValues { it.value.size }
        lengthToCount.toSortedMap().forEach { (length, count) ->
            printf("%5d | %,8d | %,8d", length, count, matchedByLength.getOrDefault(length, 0))
        }
        printf("%5s | %,8d | %,8d", "total", lengthToCount.values.sum(), matchedByLength.values.sum())
    }

    fun showSimulationTime(startTime: ZonedDateTime, keystrokes: Long) {
        val virtualDateTime = startTime.plusSeconds(keystrokes)
        printf("%16.16s | %24.24s | %,12d", dateTimeStr(ZonedDateTime.now()), dateTimeStr(virtualDateTime), keystrokes)
    }

    fun dateTimeStr(dateTime: ZonedDateTime) = pattern.format(dateTime)
}