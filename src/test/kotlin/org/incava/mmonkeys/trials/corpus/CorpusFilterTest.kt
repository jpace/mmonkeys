package org.incava.mmonkeys.trials.corpus

import org.incava.ikdk.io.Console
import org.incava.ikdk.math.Maths
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.testutil.ResourceUtil
import org.junit.jupiter.api.Test
import java.math.BigInteger
import kotlin.math.pow

class CorpusFilterTest {
    val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE, -1)

    @Test
    fun duplicates() {
        val obj = CorpusFilter(words)
        val result = obj.missingTwos
        result.forEach { (first, nexts) ->
            if (nexts.contains(first)) {
                println(first)
            }
        }
    }

    @Test
    fun twos() {
        val obj = CorpusFilter(words)
        val result = obj.missingTwos
        Console.info("result", result)
    }

    @Test
    fun threes() {
        val obj = CorpusFilter(words)
        val result = obj.missingThrees
        Console.info("result", result)
        result.forEach { (first, seconds) ->
            Console.info("first", first)
            Console.info("seconds", seconds)
        }
    }

    @Test
    fun percentPresent() {
        val unique = words.toSet()

        System.out.printf(
            "%3s | %12s | %12s | %16s | %16s | %16s | %s\n",
            "len", "unique.#", "total.#", "uniq / total", "uniq / poss", "total / poss", "possible.#"
        )

        (1..30).forEach { length ->
            val totalCount = words.filter { it.length == length }.size
            val uniqueCount = unique.filter { it.length == length }.size

//            Console.info("length", length)
//            Console.info("total.#", totalCount)
//            Console.info("unique.#", uniqueCount)
//            Console.info("possible", possible)
//            Console.info("unique / total", uniqueCount.toDouble() / totalCount)
//            Console.info("unique / possible", uniqueCount.toDouble() / possible)
//            Console.info("total / possible", totalCount.toDouble() / possible)
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