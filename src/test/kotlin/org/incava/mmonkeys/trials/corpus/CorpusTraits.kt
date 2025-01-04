package org.incava.mmonkeys.trials.corpus

import org.incava.ikdk.io.Console.printf
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.util.ResourceUtil
import java.math.BigInteger

object CorpusTraits {
    fun showEstimateMatchTimes() {
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)
        printf("word: %,d (%s)", words.size, "total")
        printf("word: %,d (%s)", words.toSet().size, "unique")
        val bySize = words.groupBy { it.length }
        bySize.toSortedMap().forEach { (length, words) ->
            printf("%d: %,d (%s)", length, words.size, "total")
            printf("%d: %,d (%s)", length, words.toSet().size, "unique")
            val y = BigInteger.valueOf(26L)
            val seconds = y.pow(length)
            if (words.size < 5) {
                println("words: $words")
            }
            printf("seconds: %,d", seconds)
            val minutes = seconds / BigInteger.valueOf(60)
            printf("minutes: %,d", minutes)
            val hours = minutes / BigInteger.valueOf(60)
            printf("hours: %,d", hours)
            val days = hours / BigInteger.valueOf(24)
            printf("days: %,d", days)
            val years = days / BigInteger.valueOf(365)
            printf("years: %,d", years)
            println()
        }
    }

    fun showDuplicatedChars() {
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)
            .distinct()
            .map(String::toLowerCase)
            .sorted()
        val chars = ('a'..'z')
        chars.forEach { first ->
            chars.forEach { second ->
                val str = "$first$second"
                val match = words.find { it.contains(str) }
                printf("%s - %s", str, match ?: "")
            }
        }
    }

    fun showByLength() {
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)
        printf("word: %,d (%s)", words.size, "total")
        val bySize = words.groupBy { it.length }
        bySize.toSortedMap().forEach { (length, words) ->
            printf("%d: %,d (%s)", length, words.size, "total")
        }
    }
}

fun main() {
    CorpusTraits.showDuplicatedChars()
    CorpusTraits.showEstimateMatchTimes()
    CorpusTraits.showByLength()
}