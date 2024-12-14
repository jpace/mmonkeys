package org.incava.mmonkeys.trials.corpus

import org.incava.ikdk.io.Console
import org.incava.ikdk.math.Maths
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.testutil.ResourceUtil
import java.math.BigInteger
import kotlin.math.pow

class CorpusFilterTrial {
    val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)

    fun duplicates() {
        val obj = CorpusFilter(words)
        val result = obj.missingTwos
        result.forEach { (first, nexts) ->
            if (nexts.contains(first)) {
                println(first)
            }
        }
    }

    fun twos() {
        val obj = CorpusFilter(words)
        val result = obj.missingTwos
        result.forEach { (first, seconds) ->
            Console.info("$first", seconds)
        }
    }

    fun threes() {
        val obj = CorpusFilter(words)
        val result = obj.missingThrees
        result.forEach { (first, seconds) ->
            seconds.forEach { (x, y) -> Console.info("$first $x", y)}
        }
    }

    fun percentPresent() {
        val unique = words.toSet()

        System.out.printf(
            "%3s | %12s | %12s | %16s | %16s | %16s | %s\n",
            "len", "unique.#", "total.#", "uniq / total", "uniq / poss", "total / poss", "possible.#"
        )

        (1..30).forEach { length ->
            val totalCount = words.filter { it.length == length }.size
            val uniqueCount = unique.filter { it.length == length }.size
            if (length > 13) {
                showStats(length, totalCount, uniqueCount)
            } else {
                val possible = 26.0.pow(length)
                System.out.printf(
                    "%-3d | %,12d | %,12d | %16.10f | %16.10f | %16.10f | %32s\n",
                    length, uniqueCount, totalCount,
                    uniqueCount.toDouble() / totalCount,
                    uniqueCount.toDouble() / possible,
                    totalCount.toDouble() / possible,
                    possible.toLong()
                )
            }
            println()
        }
    }

    fun showStats(length: Int, totalCount: Int, uniqueCount: Int) {
        val possible = Maths.powerBigRepeat(BigInteger.valueOf(26), length).toBigDecimal()
        val uniqPct = uniqueCount.toBigDecimal() / possible
        System.out.printf(
            "%-3d | %,12d | %,12d | %16.10f | %16.10f | %16.10f | %32s\n",
            length, uniqueCount, totalCount,
            uniqueCount.toDouble() / totalCount,
            uniqueCount.toBigDecimal() / possible,
            totalCount.toBigDecimal() / possible,
            possible.toBigInteger()
        )
    }
}

fun main() {
    val obj = CorpusFilterTrial()
    obj.duplicates()
    obj.percentPresent()
    obj.twos()
    obj.threes()
}